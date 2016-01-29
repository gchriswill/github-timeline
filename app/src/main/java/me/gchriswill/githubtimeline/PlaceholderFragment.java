package me.gchriswill.githubtimeline;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends ListFragment implements CompleteTaskListener,
        AdapterView.OnItemClickListener {

    AsyncHttpRequest request;
    XMLPullParserHandler parser;

    List<News> entries = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_fragment, container, false);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(true);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        request = new AsyncHttpRequest(getActivity(), this, 0);
        request.execute(getResources().getString(R.string.url_feed));

    }

    @Override
    public void completeTask(String result, int response_code) {

        switch (response_code) {
            case 0:

                if( result != null) {

                    Log.i("COMPLETE TASK", "Response :\n" + result);

                }

                parser = new XMLPullParserHandler();

                setEntries(parser, result);

                NewsAdapter adapter = new NewsAdapter(getActivity(), entries);

                setListAdapter(adapter);

                getListView().setOnItemClickListener(this);

                break;

            default:

                break;

        }

    }
    private void setEntries(XMLPullParserHandler parser,String result) {

        InputStream is = new ByteArrayInputStream(result.getBytes() );

        entries = parser.parse(is);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(getActivity(),DetailActivity.class).putExtra("entry",
                entries.get(position) ) );

    }

}
