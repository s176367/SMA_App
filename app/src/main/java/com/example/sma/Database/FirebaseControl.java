package com.example.sma.Database;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

// import com.example.sma.Model.Contact;
import com.example.sma.Model.ContactIDObject;
import com.example.sma.Model.ContactInvite;
import com.example.sma.Model.MeetingIDObject;
import com.example.sma.Model.MeetingObject;
import com.example.sma.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseControl implements IFirebaseControl {

    FirebaseFirestore FC = FirebaseFirestore.getInstance();

    String TAG = this.getClass().getName();

    public static FirebaseControl fc = new FirebaseControl();


    // User
    @Override
    public void createUser(User user, final SenderCallback senderCallback) {
        user.setUserID(FirebaseAuth.getInstance().getUid());
        FC.collection("users").document(user.getUserID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                senderCallback.onSuccess();
                Log.d(TAG, " User created in database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
                Log.d(TAG, "User failed to crate in database");
            }
        });
    }

    @Override
    public void getUser(final String userId, final ReceiverCallback receiverCallback) {
        DocumentReference dr = FC.collection("users").document(userId);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    if (ds.exists()) {
                        User user = ds.toObject(User.class);
                        LocalDatabase.LD.addContact(user);
                        receiverCallback.onSuccess(task);
                        Log.d(TAG, "Database successfully retrived user");
                    } else {
                        receiverCallback.noData();
                    }
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                receiverCallback.onFailure(e);
                Log.d(TAG, "Database failed to get user");
            }
        });
    }


    //Meeting
    @Override
    public void createMeeting(final MeetingObject meetingObject, final SenderCallback senderCallback) {

        FC.collection("meetings").add(meetingObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(final DocumentReference documentReference) {

                FC.collection("meetings").document(documentReference.getId()).update("id", documentReference.getId());

                insertMeetingID(documentReference.getId(), new SenderCallback() {

                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Meeting successfully created in database");
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        senderCallback.onFailure(exception);
                        Log.d("TAG", "insertMeetingID failed");

                    }
                });

                for (int i = 0; i < meetingObject.getParticipants().size(); i++) {
                    inviteParticipant(meetingObject.getParticipants().get(i), documentReference.getId(), new SenderCallback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Participant invited");
                            senderCallback.onSuccess();
                        }

                        @Override
                        public void onFailure(Exception exception) {
                            Log.d(TAG, "Participant invite failed");
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
                Log.d(TAG, "Create meeting failed in database");
            }
        });
    }

    @Override
    public void getMeeting(final String MeetingId, final ReceiverCallback receiverCallback) {
        DocumentReference dr = FC.collection("meetings").document(MeetingId);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot ds = task.getResult();
                    MeetingObject meetingObject = ds.toObject(MeetingObject.class);
                    LocalDatabase.LD.addMeeting(meetingObject);
                    receiverCallback.onSuccess(task);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                receiverCallback.onFailure(e);
                Log.d(TAG, "Failed to receive meeting from database");
            }
        });
    }


    @Override
    public void retrieveAllMeetings(final CollectionReceiverCallback receiverCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                LocalDatabase.LD.deleteMeetingList();
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        System.out.println(task.getResult());
                        receiverCallback.noData();
                    }
                    else {
                        for (DocumentSnapshot document : task.getResult()) {
                            MeetingIDObject mtObject = document.toObject(MeetingIDObject.class);
                            getMeeting(mtObject.getMeetingID(), new ReceiverCallback() {
                                @Override
                                public void onSuccess(Task<DocumentSnapshot> task1) {
                                    Log.d(TAG, "getMeeting success");
                                    receiverCallback.onSuccess(task);

                                }

                                @Override
                                public void onFailure(Exception exception) {
                                    Log.d(TAG, "getMeeting failed");
                                }

                                @Override
                                public void noData() {
                                    Log.d(TAG, "meeting does not exist");
                                }
                            });
                        }
                    }

                }
                if (task.getResult().isEmpty()) {
                    receiverCallback.noData();
                }
            }
        });
    }

    @Override
    public void deleteMeeting(final String meetingId, final SenderCallback senderCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        MeetingIDObject mtObject = document.toObject(MeetingIDObject.class);
                        if (mtObject.getMeetingID().equals(meetingId)) {
                            FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                                    .document(mtObject.getDocID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    senderCallback.onSuccess();
                                    Log.d(TAG, "Meeting deleted successfully");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    senderCallback.onFailure(e);
                                    Log.d(TAG, "Meeting failed to delete");
                                }
                            });
                        }
                    }
                }
            }
        });
    }


    @Override
    public void insertMeetingID(final String meetingID, SenderCallback senderCallback) {
        String userId = LocalDatabase.LD.getUser().getUserID();
        MeetingIDObject meetingIDObject = new MeetingIDObject(meetingID);
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings").add(meetingIDObject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                                .document(documentReference.getId()).update("docID", documentReference.getId());
                        getMeeting(meetingID, new ReceiverCallback() {
                            @Override
                            public void onSuccess(Task<DocumentSnapshot> task) {

                            }

                            @Override
                            public void onFailure(Exception exception) {

                            }

                            @Override
                            public void noData() {

                            }
                        });
                    }

                });
    }


    //Invite

    @Override
    public void inviteParticipant(final String userID, String meetingID, final SenderCallback senderCallback) {
        final MeetingIDObject meetingIDObject = new MeetingIDObject(meetingID);
        FC.collection("users").document(userID).collection("meetingInvites").add(meetingIDObject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        FC.collection("users").document(userID).collection("meetingInvites")
                                .document(documentReference.getId()).update("docID", documentReference.getId());
                        senderCallback.onSuccess();
                    }
                });
    }

    @Override
    public void getInvite(String inviteUserID, final ReceiverCallback receiverCallback) {
        DocumentReference dr = FC.collection("users").document(inviteUserID);
        dr.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    User userInvite = document.toObject(User.class);
                    LocalDatabase.LD.addInvite(userInvite);
                    receiverCallback.onSuccess(task);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                receiverCallback.onFailure(e);
            }
        });
    }

    @Override
    public void getMeetingInvite(String meetingID, final ReceiverCallback receiverCallback) {

        FC.collection("meetings").document(meetingID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    MeetingObject meetingInvite = document.toObject(MeetingObject.class);
                    LocalDatabase.LD.addMeetingInvite(meetingInvite);
                    receiverCallback.onSuccess(task);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                receiverCallback.onFailure(e);
            }
        });
    }

    @Override
    public void retrieveAllMeetingInvites(final CollectionReceiverCallback receiverCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetingInvites")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                LocalDatabase.LD.deleteMeetingInviteList();
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()){
                        receiverCallback.noData();
                    }
                    else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MeetingIDObject meetingIDObject = document.toObject(MeetingIDObject.class);
                            getMeetingInvite(meetingIDObject.getMeetingID(), new ReceiverCallback() {
                                @Override
                                public void onSuccess(Task<DocumentSnapshot> task1) {
                                    receiverCallback.onSuccess(task);
                                    Log.d(TAG, "Invite successfully retrieved");
                                }

                                @Override
                                public void onFailure(Exception exception) {
                                    receiverCallback.onFailure(exception);
                                    Log.d(TAG, "Failed to get invite");
                                }

                                @Override
                                public void noData() {
                                    receiverCallback.noData();
                                    Log.d(TAG, "No data received");

                                }
                            });
                        }
                    }

                }
            }
        });
    }

    @Override
    public void acceptMeetingRequest(String meetingID, SenderCallback senderCallback) {
        final MeetingIDObject meeting = new MeetingIDObject(meetingID);


        insertMeetingID(meetingID, new SenderCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });

        getMeeting(meetingID, new ReceiverCallback() {
            @Override
            public void onSuccess(Task<DocumentSnapshot> task) {
                Log.d(TAG, "Meetings invitation accepted");

            }

            @Override
            public void onFailure(Exception exception) {
                Log.d(TAG, "Meeting accept failed");

            }

            @Override
            public void noData() {
                Log.d(TAG, "Meeting did not exist");

            }
        });


        deleteMeetingRequest(meetingID, new SenderCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });

    }

    @Override
    public void deleteMeetingRequest(final String meetingID, final SenderCallback senderCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetingInvites").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                MeetingIDObject meetingIDObject = doc.toObject(MeetingIDObject.class);
                                if (meetingIDObject.getMeetingID().equals(meetingID)) {
                                    FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetingInvites")
                                            .document(meetingIDObject.getDocID()).delete();
                                    senderCallback.onSuccess();
                                    Log.d(TAG, "Meeting invite request deleted");
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
                Log.d(TAG, "Contact request failed to delete");
            }
        });

    }

    @Override
    public void retriveAllInvites(final CollectionReceiverCallback receiverCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("contactInvites")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                LocalDatabase.LD.deleteInviteList();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ContactIDObject user = document.toObject(ContactIDObject.class);
                        getInvite(user.getUserID(), new ReceiverCallback() {
                            @Override
                            public void onSuccess(Task<DocumentSnapshot> task1) {
                                receiverCallback.onSuccess(task);
                                Log.d(TAG, "Invite successfully retrieved");
                            }

                            @Override
                            public void onFailure(Exception exception) {
                                receiverCallback.onFailure(exception);
                                Log.d(TAG, "Failed to get invite");
                            }

                            @Override
                            public void noData() {
                                receiverCallback.noData();
                                Log.d(TAG, "No data received");

                            }
                        });
                    }


                }
            }
        });
    }


    //Contact
    @Override
    public void acceptContactRequest(final String senderID, final String receiverID, final SenderCallback senderCallback) {

        final ContactIDObject sender = new ContactIDObject(senderID);
        final ContactIDObject receiver = new ContactIDObject(receiverID);

        FC.collection("users").document(senderID).collection("contacts").add(receiver)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        receiver.setDocID(documentReference.getId());
                        FC.collection("users").document(senderID).collection("contacts").document
                                (documentReference.getId()).update("docID", receiver.getDocID());
                        senderCallback.onSuccess();
                        Log.d(TAG, "Contact request accepted");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
                Log.d(TAG, "Contact request accept failed");
            }
        });


        FC.collection("users").document(receiverID).collection("contacts").add(sender)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        sender.setDocID(documentReference.getId());
                        FC.collection("users").document(receiverID).collection("contacts").document
                                (documentReference.getId()).update("docID", sender.getDocID());


                    }
                });


        deleteContactRequest(receiverID, new SenderCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception exception) {

            }
        });
    }

    @Override
    public void deleteContactRequest(final String receiverID, final SenderCallback senderCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("contactInvites").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                ContactInvite contactInvite = doc.toObject(ContactInvite.class);
                                if (contactInvite.getUserID().equals(receiverID)) {
                                    FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("contactInvites")
                                            .document(contactInvite.getDocID()).delete();
                                    senderCallback.onSuccess();
                                    Log.d(TAG, "Contact request deleted");
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
                Log.d(TAG, "Contact request failed to delete");
            }
        });
    }

    @Override
    public void contactRequest(final String rEmail, final CollectionReceiverCallback receiverCallback) {
        // Det ID man vil finde
        final String email = rEmail;


        FC.collection("users").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    final List<User> emailCorrect = document.toObjects(User.class);

                    if (rEmail.isEmpty()) {
                        receiverCallback.noData();
                    } else if (emailCorrect.isEmpty()) {
                        Exception e = new Exception("ABVC");
                        receiverCallback.onFailure(e);
                    }
                    // Man kan ikke tilføje sig selv
                    else if (emailCorrect.get(0).getUserID().equals(FirebaseAuth.getInstance().getUid())) {
                        Exception e = new Exception("Cannot add own email");
                        receiverCallback.onFailure(e);
                    } else {
                        checkContact(emailCorrect.get(0).getUserID(), new CollectionReceiverCallback() {
                            @Override
                            public void onSuccess(final Task<QuerySnapshot> task) {
                                inviteContact(emailCorrect.get(0).getEmail(), new SenderCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d(TAG, "User successfully invited");
                                        receiverCallback.onSuccess(task);
                                    }

                                    @Override
                                    public void onFailure(Exception exception) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Exception exception) {
                            }

                            @Override
                            public void noData() {
                                Log.d(TAG, "User already contact or invited.");
                                Exception e = new Exception("User alreadt contact or invited");
                                receiverCallback.onFailure(e);
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    public void inviteContact(String email, final SenderCallback senderCallback) {

        FC.collection("users").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    List<User> emailCorrect = document.toObjects(User.class);
                    final String contactID = emailCorrect.get(0).getUserID();
                    ContactInvite CI = new ContactInvite(FirebaseAuth.getInstance().getUid());
                    FC.collection("users").document(contactID).collection("contactInvites")
                            .add(CI).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            FC.collection("users").document(contactID).collection("contactInvites")
                                    .document(documentReference.getId()).update("docID", documentReference.getId());
                            senderCallback.onSuccess();
                        }
                    });
                }

            }
        });
    }

    @Override
    public void checkContact(final String UserID, final CollectionReceiverCallback receiverCallback) {
        final boolean[] check = new boolean[2];

        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("contacts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        ContactIDObject user = snapshot.toObject(ContactIDObject.class);
                        System.out.println("Data" + user.getUserID());
                        System.out.println("UserID " + UserID);


                        if (UserID.equals(user.getUserID())) {
                            check[0] = true;
                            break;
                        } else {
                            check[0] = false;
                        }
                    }

                }
            }
        });
        FC.collection("users").document(UserID).collection("contactInvites").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                ContactIDObject user = documentSnapshot.toObject(ContactIDObject.class);
                                if (user.getUserID().equals(FirebaseAuth.getInstance().getUid())) {
                                    check[1] = true;
                                    break;

                                } else {
                                    check[1] = false;
                                }
                            }
                        }
                        if (!check[0] && !check[1]) {
                            receiverCallback.onSuccess(task);
                        } else receiverCallback.noData();
                    }

                });
    }




                @Override
                public void retriveAllContacts(final CollectionReceiverCallback receiverCallback) {

                    FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("contacts")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                            if (task.isSuccessful())
                                LocalDatabase.LD.deleteContactList();
                            for( QueryDocumentSnapshot doc : task.getResult()) {
                                ContactIDObject CIDObject = doc.toObject(ContactIDObject.class);
                                getUser(CIDObject.getUserID(), new ReceiverCallback() {
                                    @Override
                                    public void onSuccess(Task<DocumentSnapshot> task1) {
                                        receiverCallback.onSuccess(task);
                                        Log.d(TAG, "Contactlist retrived");
                                    }
                                    @Override
                                    public void onFailure(Exception exception) {
                                        Log.d(TAG, "Contact list failed to retrive");
                                    }
                                    @Override
                                    public void noData() {
                                        Log.d(TAG,  "Contact list failed, no data");
                                    }
                                });
                            }
                            if (task.getResult().isEmpty()){
                                receiverCallback.noData();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            receiverCallback.onFailure(e);

                        }
                    });

                }


            }
