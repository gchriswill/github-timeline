package me.gchriswill.githubtimeline;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import java.util.List;

public class FavoriteFragment extends ListFragment implements AdapterView.OnItemClickListener {

    List<News> entries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favorite_fragment, container, false);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FileStore fs=new FileStore();
        entries = fs.fileReader(getActivity() );

        if( entries.size() > 0 ) {

            NewsAdapter adapter=new NewsAdapter(getActivity(), fs.fileReader(getActivity() ) );

            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity( new Intent( getActivity(), DetailActivity.class).putExtra("entry",
                entries.get(position) ) );

    }

}
