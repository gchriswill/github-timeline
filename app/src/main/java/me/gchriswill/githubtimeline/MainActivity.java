package me.gchriswill.githubtimeline;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new PlaceholderFragment() ).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId() ) {

            case R.id.action_info:

                showDialog("News Reader", "This is a news reader app that displays the global " +
                        "timeline from github.com");

                break;

            case R.id.action_detail:

                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new PlaceholderFragment() ).commit();

                break;

            case R.id.action_simple_list:

                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new FavoriteFragment() ).commit();

                break;

            default:

                showDialog("News Reader", "This is option the default option... ");

                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private void showDialog(String title,String message) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){

                    case DialogInterface.BUTTON_POSITIVE:

                        dialog.dismiss();
                        break;

                    default:

                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message).setPositiveButton("Ok",
                dialogClickListener).show();

    }
}
