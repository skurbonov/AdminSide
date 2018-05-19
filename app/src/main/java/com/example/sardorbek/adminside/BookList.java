package com.example.sardorbek.adminside;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.sardorbek.adminside.Common.Common;
import com.example.sardorbek.adminside.Interface.ItemClickListener;
import com.example.sardorbek.adminside.Model.Book;
import com.example.sardorbek.adminside.Model.Category;
import com.example.sardorbek.adminside.ViewHolder.BookViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import info.hoang8f.widget.FButton;

public class BookList extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    RelativeLayout rootLayout;

    FloatingActionButton fab;

    //Firebase init
    FirebaseDatabase db;
    DatabaseReference bookList;
    FirebaseStorage storage;
    StorageReference storageReference;

    String categoryId="";

    FirebaseRecyclerAdapter<Book,BookViewHolder> adapter;

    MaterialEditText edtTitle, edtAuthor,edtDescription, edtPageCount, edtDate, edtIsbn;
    FButton btnSelect,btnUpload;

    Book newBook;
    Uri saveUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        db = FirebaseDatabase.getInstance();
        bookList=db.getReference("Book");
        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        recyclerView=(RecyclerView)findViewById(R.id.recycler_book);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rootLayout=(RelativeLayout)findViewById(R.id.rootLayout);

        fab=(FloatingActionButton)findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBookDialog();
            }
        });

        if (getIntent()!=null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty())
            loadListBook(categoryId);

    }

    private void showAddBookDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookList.this);
        alertDialog.setTitle("Add new Book");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater=this.getLayoutInflater();
        View add_new_main_layout =inflater.inflate(R.layout.add_new_book_layout,null);

        edtTitle= add_new_main_layout.findViewById(R.id.edtTitle);
        edtAuthor=add_new_main_layout.findViewById(R.id.edtAuthor);
        edtDescription=add_new_main_layout.findViewById(R.id.edtDescription);
        edtPageCount=add_new_main_layout.findViewById(R.id.edtPageCount);
        edtIsbn=add_new_main_layout.findViewById(R.id.edtIsbn);
        edtDate=add_new_main_layout.findViewById(R.id.edtDate);



        btnSelect=add_new_main_layout.findViewById(R.id.btn_select);
        btnUpload=add_new_main_layout.findViewById(R.id.btn_upload);

        //event for button

        btnSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialog.setView(add_new_main_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        //set button

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // new category
                if(newBook!=null)
                {
                    bookList.push().setValue(newBook);
                    Snackbar.make(rootLayout,"New Category "+newBook.getTitle()+" was added",Snackbar.LENGTH_SHORT).show();

                }

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }


    private void uploadImage() {
        if(saveUri!=null)
        {
            final ProgressDialog mDialog=new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder=storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(BookList.this, "Uploaded!", Toast.LENGTH_SHORT).show();

                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value  for newCategory if image upload and we can get download link
                                    newBook=new Book();
                                    newBook.setTitle(edtTitle.getText().toString());
                                    newBook.setAuthor(edtAuthor.getText().toString());
                                    newBook.setDescription(edtDescription.getText().toString());
                                    newBook.setPageCount(edtPageCount.getText().toString());
                                    newBook.setiSBN(edtIsbn.getText().toString());
                                    newBook.setPublishDate(edtDate.getText().toString());
                                    newBook.setCategoryId(categoryId);
                                    newBook.setImage(uri.toString());

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(BookList.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded"+progress+"%");
                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), Common.PICK_IMAGE_REQUEST);
    }

    private void loadListBook(String categoryId) {
        adapter =new FirebaseRecyclerAdapter<Book, BookViewHolder>(
                Book.class,
                R.layout.book_item,
                BookViewHolder.class,
                bookList.orderByChild("categoryId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(BookViewHolder viewHolder, Book model, int position) {
                viewHolder.txtBookName.setText(model.getTitle());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(viewHolder.imageBookView);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==Common.PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null) {
            saveUri = data.getData();
            btnSelect.setText("Image Selected !");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateFoodDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        else if(item.getTitle().equals(Common.DELETE))
        {
            deleteBook(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);

    }

    private void deleteBook(String key) {
        bookList.child(key).removeValue();
    }

    private void showUpdateFoodDialog(final String key, final Book item) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BookList.this);
        alertDialog.setTitle("Edit Book");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater=this.getLayoutInflater();
        View add_new_main_layout =inflater.inflate(R.layout.add_new_book_layout,null);

        edtTitle= add_new_main_layout.findViewById(R.id.edtTitle);
        edtAuthor=add_new_main_layout.findViewById(R.id.edtAuthor);
        edtDescription=add_new_main_layout.findViewById(R.id.edtDescription);
        edtPageCount=add_new_main_layout.findViewById(R.id.edtPageCount);
        edtIsbn=add_new_main_layout.findViewById(R.id.edtIsbn);
        edtDate=add_new_main_layout.findViewById(R.id.edtDate);

        //set the default value

        edtTitle.setText(item.getTitle());
        edtAuthor.setText(item.getAuthor());
        edtDescription.setText(item.getDescription());
        edtIsbn.setText(item.getiSBN());
        edtDate.setText(item.getPublishDate());
        edtPageCount.setText(item.getPageCount());




        btnSelect=add_new_main_layout.findViewById(R.id.btn_select);
        btnUpload=add_new_main_layout.findViewById(R.id.btn_upload);

        //event for button

        btnSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });

        alertDialog.setView(add_new_main_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        //set button

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // new category

                    //update information
                    item.setTitle(edtTitle.getText().toString());
                    item.setAuthor(edtAuthor.getText().toString());
                    item.setDescription(edtDescription.getText().toString());
                    item.setiSBN(edtIsbn.getText().toString());
                    item.setPageCount(edtPageCount.getText().toString());
                    item.setPublishDate(edtDate.getText().toString());

                    bookList.child(key).setValue(item);
                    Snackbar.make(rootLayout," The Book "+item.getTitle()+" was edited",Snackbar.LENGTH_SHORT).show();



            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();

    }



    private void changeImage(final Book item) {
        if(saveUri!=null)
        {
            final ProgressDialog mDialog=new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder=storageReference.child("images/"+imageName);
            imageFolder.putFile(saveUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mDialog.dismiss();
                            Toast.makeText(BookList.this, "Uploaded!", Toast.LENGTH_SHORT).show();

                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value  for newCategory if image upload and we can get download link
                                    item.setImage(uri.toString());

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(BookList.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploaded"+progress+"%");
                        }
                    });
        }
    }


}
