package me.gchriswill.githubtimeline;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    TextView tvPublished, tvUpdated, tvLink, tvName, tvTitle, tvUri, tvEmail;
    ImageView userImage;
    Bitmap profilePicBitmap;
    News entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvPublished = (TextView)findViewById(R.id.tvPublished);
        tvUpdated = (TextView)findViewById(R.id.tvUpdated);
        tvLink = (TextView)findViewById(R.id.tvLink);
        tvName = (TextView)findViewById(R.id.tvName);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvUri = (TextView)findViewById(R.id.tvUri);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        userImage = (ImageView) findViewById(R.id.user_image_view);

        entry = (News) getIntent().getSerializableExtra("entry");

        tvPublished.setText(entry.published);
        tvUpdated.setText(entry.updated);
        tvLink.setText(entry.link);
        tvName.setText(entry.name);
        tvTitle.setText(entry.title);
        tvUri.setText(entry.uri);
        tvEmail.setText(entry.email);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        new GetBitmapImageFromUrl().execute(entry.imgUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId() ) {

            case R.id.action_share:

                if(entry.link != null && !entry.link.equals("") ){

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, entry.link);
                    startActivity(Intent.createChooser(sharingIntent, "Share using") );

                }

                break;

            case R.id.action_view:

                if(entry.link != null && !entry.link.equals("") ){

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(entry.link));
                    startActivity(browserIntent);

                }

                break;

            case R.id.action_favourite:

                try {

                    FileStore fs = new FileStore();
                    fs.fileWriter(entry, this);

                } catch(Exception e) {

                    Toast.makeText(this, "News not saved", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

                break;

            default:

                break;

        }

        return super.onOptionsItemSelected(item);

    }

    class GetBitmapImageFromUrl extends AsyncTask<String,Void,Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... params) {

            try {

                profilePicBitmap = BitmapFactory.decodeStream((InputStream)
                        new URL(params[0]).getContent() );

            } catch (IOException e) {

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            userImage.setImageBitmap(profilePicBitmap);

        }

    }

}

