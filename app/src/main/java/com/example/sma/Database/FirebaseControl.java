package com.example.sma.Database;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FirebaseControl implements IFirebaseControl {

    FirebaseFirestore FC = FirebaseFirestore.getInstance();

    public static FirebaseControl fc = new FirebaseControl();



    @Override
    public void createUser(User user, final SenderCallback senderCallback) {
        user.setUserID(FirebaseAuth.getInstance().getUid());
        FC.collection("users").document(user.getUserID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                senderCallback.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                senderCallback.onFailure(e);
            }
        });
        FC.collection("meetings").document().getId();
    }



    @Override
    public void createMeeting(final MeetingObject meetingObject, final SenderCallback senderCallback) {

        FC.collection("meetings").add(meetingObject).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(final DocumentReference documentReference) {
                senderCallback.onSuccess();
                FC.collection("meetings").document(documentReference.getId()).update("id", documentReference.getId());

                insertMeetingID(documentReference.getId(), new SenderCallback() {

                    @Override
                    public void onSuccess() {
                    }
                    @Override
                    public void onFailure(Exception exception) {

                    }
                });


            }
        });
    }



    @Override
    public void retrieveAllMeetings(final ReceiverCallback receiverCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                    LocalDatabase.LD.deleteMeetingList();
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        MeetingIDObject mtObject = document.toObject(MeetingIDObject.class);
                        getMeeting(mtObject.getMeetingID(), new ReceiverCallback() {
                            @Override
                            public void onSuccess(Task<DocumentSnapshot> task) {
                                receiverCallback.onSuccess(task);
                            }
                            @Override
                            public void onFailure(Exception exception) {
                                receiverCallback.onFailure(exception);
                            }
                            @Override
                            public void noData() {
                                receiverCallback.noData();
                            }
                        });
                    }
                }
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
        });
    }

    @Override
    public void deleteMeeting(final String meetingId, final SenderCallback senderCallback) {
        FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        MeetingIDObject mtObject = document.toObject(MeetingIDObject.class);
                        if (mtObject.getMeetingID().equals(meetingId)){
                            FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("meetings")
                                    .document(mtObject.getDocID()).delete();
                            break;
                        }
                    }

                    }
                }
            });
        }
    @Override
    public void insertMeetingID(final String meetingID, SenderCallback senderCallback) {
        String userId = LocalDatabase.LD.getUser().getUserID();
        /**Map<String, Object> meetingIDmap = new HashMap<>();
         meetingIDmap.put("meetingID", meetingID); */
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



        @Override
        public void contactRequest(final String userID, SenderCallback senderCallback) {

            // Det ID man vil finde
            final String user =  userID;

            FC.collection("users").whereEqualTo("email", userID)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.getString("email").equals(userID)) {
                                String contactID = document.getString("userID");
                                ContactInvite CI = new ContactInvite(FirebaseAuth.getInstance().getUid());
                                FC.collection("users").document(contactID).collection("contactInvites").add(CI);

                            }
                        }
                    }
                }
            });
        }

        @Override
        public void retriveAllContacts(final ReceiverCallback receiverCallback) {

            FC.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("contacts")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            ContactIDObject CIDObject = document.toObject(ContactIDObject.class);


                            getUser(CIDObject.getUserID(), new ReceiverCallback() {
                                @Override
                                public void onSuccess(Task<DocumentSnapshot> task) {
                                    receiverCallback.onSuccess(task);
                                }
                                @Override
                                public void onFailure(Exception exception) {
                                    receiverCallback.onFailure(exception);
                                }
                                @Override
                                public void noData() {
                                    receiverCallback.noData();
                                }
                            });


                        }
                    }
                    else{
                        System.out.println("error");
                    }
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
                        User user = ds.toObject(User.class);
                        LocalDatabase.LD.addContact(user);
                        receiverCallback.onSuccess(task);
                    }
                }

            });
        }

    }
