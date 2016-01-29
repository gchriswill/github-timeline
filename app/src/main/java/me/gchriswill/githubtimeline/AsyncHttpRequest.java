package me.gchriswill.githubtimeline;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

    ProgressDialog dialog;
    Context context;
    CompleteTaskListener listener;

    int response_code;

    public AsyncHttpRequest(Context context, Object class_name,int res_code) {

        this.context = context;
        listener = (CompleteTaskListener) class_name;
        this.response_code=res_code;

    }

    @Override
    protected void onPreExecute() {

        if (HttpRequest.hasConnection(context) ) {

            dialog = new ProgressDialog(context);
            dialog.setMessage("loading");
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {

                    dialog.dismiss();
                    cancel(true);

                    Toast.makeText(context, "request cancelled by user!", Toast.LENGTH_LONG).show();

                }

            });

            dialog.show();

        } else {

            cancel(true);
            Toast.makeText(context, HttpRequest.NETWORK_MESSAGE, Toast.LENGTH_SHORT).show();

        }

        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {


        String result = null;

        try {

            HttpRequest handler = new HttpRequest();

            result=handler.makeServiceCall(params[0]);

        } catch (final Exception e) {

            ( (Activity) context).runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    cancel(true);
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            });

        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        if (dialog.isShowing() ) {

            dialog.dismiss();

        }

        if (result != null) {

            listener.completeTask(result,response_code);

        }

        super.onPostExecute(result);

    }

}
