package com.recipes.foodhub.views;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.recipes.foodhub.R;
import com.recipes.foodhub.model.recipe;
import com.recipes.foodhub.model.user;
import com.recipes.foodhub.presenter.IRescips_manager;
import com.recipes.foodhub.presenter.recipes_manager;
import com.recipes.foodhub.presenter.user_manager;
import com.squareup.picasso.Picasso;

public class addRecipe extends AppCompatActivity {

    private EditText title_recipe,ingdedient,how_to;
    private IRescips_manager myrecipe;
    private recipe recipe;
    private Context context;
    public  static  final  int gallary_intent=2;
    private static final int PICK_IMAGE_REQUEST = 1;


    private Button mButtonUpload;

    private EditText mEditTextFileName;
    private ImageView mImageView;


    private Uri mImageUri;

    private StorageReference mStorageRef;


    private StorageTask mUploadTask;
    private user userInfo;
    private com.recipes.foodhub.presenter.user_manager user_manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        context=this;

        title_recipe=findViewById(R.id.id_title);
        ingdedient=findViewById(R.id.id_ingdedient);
        how_to=findViewById(R.id.id_how_to);
        myrecipe =new recipes_manager(this);
        user_manager=new user_manager(context);
        recipe=new recipe();


        mImageView = findViewById(R.id.upload_image);
        mButtonUpload=findViewById(R.id.mButtonUpload);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(addRecipe.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();

                }
            }
        });




    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(mImageView);
            mImageView.setPadding(0,0,0,0);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    recipe.setUrl_image(url.toString());
                                            uploadinfo();
                                    Toast.makeText(addRecipe.this, "Upload successful"+mImageUri, Toast.LENGTH_LONG).show();
                                }
                            });





                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addRecipe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){

                            }else
                                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadinfo(){

        userInfo=user_manager.getInfoFromShared();
        recipe.setTitle(title_recipe.getText().toString());
        recipe.setIngredient(ingdedient.getText().toString());
        recipe.setHow_to(how_to.getText().toString());
        recipe.setUser_id(userInfo.getUserid());
        recipe.setRanking(0);
        myrecipe.addRecipe(recipe,context);
    }

}
