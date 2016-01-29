package me.gchriswill.githubtimeline;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileStore {

    public String File = "cache";

    public void fileWriter(News news, Context inContext) {

        List<News> internalDataStored = fileReader(inContext);

        boolean check = false;

        for (News c : internalDataStored) {

            if (c.id.equals(news.id) ){

                Toast.makeText(inContext, "Already saved!", Toast.LENGTH_LONG).show();
                check = true;
                break;

            }

        }

        if (!check) {

            internalDataStored.add(news);
            Toast.makeText(inContext, "News saved successfully", Toast.LENGTH_LONG).show();

        }

        try {

            FileOutputStream fos;

            fos = inContext.openFileOutput(File, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(internalDataStored);
            oos.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @SuppressWarnings("unchecked")
    public List<News> fileReader( Context inContext) {

        FileInputStream fis;
        List<News> internalDataStored = new ArrayList<>();

        try {

            fis = inContext.openFileInput(File);
            ObjectInputStream ois = new ObjectInputStream(fis);

            internalDataStored = (List<News>) ois.readObject();

            ois.close();

        } catch (ClassNotFoundException | IOException e) {

            e.printStackTrace();

        }

        return internalDataStored;

    }

}
