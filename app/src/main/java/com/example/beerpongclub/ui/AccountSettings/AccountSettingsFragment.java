package com.example.beerpongclub.ui.AccountSettings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.beerpongclub.Database.User;
import com.example.beerpongclub.Database.UserContainer;
import com.example.beerpongclub.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class AccountSettingsFragment extends Fragment {

    private static final String TAG_IMAGE = "PROFILE_IMAGE";
    private static final String PATH_PROFILE_PIC = "profile_images";
    private static final String TAG_VALUE_EVENT = "VALUE_EVENT";


    private static final int MAX_LENGTH = 10;
    private AccountSettingsViewModel accountVM ;
    private UserContainer userContainer;
    private TextView editText_Username;
    private TextView editText_EMail;
    private CircleImageView profile_pic;
    private ImageButton button_change_profile_pic;
    private FirebaseUser mUser;
    private StorageReference mImageReff;
    private ProgressBar mProgress;
    private  CircleImageView mProfileImageView;


    private static final int GALLERY_PICK = 1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountVM = new ViewModelProvider(this).get(AccountSettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        editText_EMail = (TextView) root.findViewById(R.id.changeEMail_editText_accountSettings);
        editText_Username = (TextView) root.findViewById(R.id.changeUsername_editText_accountSettings);
        accountVM.getUsername().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG_VALUE_EVENT, "observer Username called; Username = " + s);
                editText_Username.setText(s);
            }
        });
        accountVM.getEMail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                editText_EMail.setText(s);
            }
        });
        accountVM.getImageUri().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String imageUri = s;
                if(imageUri == null || imageUri == "default") {
                    Log.d(TAG_IMAGE, "User has no Profile Picture; Displaying default");
                } else {
                    Log.d(TAG_VALUE_EVENT, "Displaying user Profile Picture");
                    Picasso.get().load(imageUri).into(mProfileImageView);
                }
            }
        });
        //addValueEventListener_username_EMail();
        button_change_profile_pic = (ImageButton) root.findViewById(R.id.changeProfilePic_imageButton);
        button_change_profile_pic.setOnClickListener(change_profile_pic_OnClickListener);
        mProfileImageView = root.findViewById(R.id.profile_account_settings);
        mProgress = root.findViewById(R.id.progressBar);
        mProgress.setVisibility(View.INVISIBLE);

        mImageReff = FirebaseStorage.getInstance().getReference();

        return root;
    }

    View.OnClickListener change_profile_pic_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG_IMAGE, "onClick");
            Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);


        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(TAG_IMAGE, "onActivityResult");


        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUri = intent.getData();
            Log.d(TAG_IMAGE, imageUri.toString());
            Toast.makeText(getContext(), imageUri.toString(), Toast.LENGTH_LONG).show();



            CropImage.activity(imageUri)
                    .setAspectRatio(1, 1)
                    .start(getContext(), this);
        }


        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(intent);
            //Toast.makeText(getContext(), "Uploading... ", Toast.LENGTH_LONG).show();
            Log.d(TAG_IMAGE, "Uploading..");
            if(resultCode == RESULT_OK) {

                final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setTitle("Uploading Image...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                mProgress = new ProgressBar(getContext());
                mProgress.setVisibility(View.VISIBLE);

                Uri resultUri = result.getUri();
                String imageName = mUser.getUid()+".jpeg";
                final StorageReference filepath = mImageReff.child(PATH_PROFILE_PIC).child(imageName);
                UploadTask uploadTask = filepath.putFile(resultUri);


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG_IMAGE, "Uploading to Firebase worked");
                            Uri downloadUri = task.getResult();
                            String download_url = downloadUri.toString();

                            UserContainer update_photoUrl = new UserContainer(new User(null, null, null, mUser.getUid(), download_url));
                            update_photoUrl.updateElement();
                            //Toast.makeText(getContext(), "Uploading to Firebase worked", Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG_IMAGE, "Upload to Firebase failed");
                            Toast.makeText(getContext(), "Upload to Firebase failed", Toast.LENGTH_LONG).show();
                        }
                        mProgress.setVisibility(View.GONE);
                        mProgressDialog.dismiss();
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(TAG_IMAGE, "Error: " + error.toString());
            }

        } else {
            Log.e(TAG_IMAGE, "Error");
        }
    }






}
