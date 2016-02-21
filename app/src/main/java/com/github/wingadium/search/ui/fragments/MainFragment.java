package com.github.wingadium.search.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.wingadium.search.R;
import com.github.wingadium.search.ui.adapter.QuestionAdapter;
import com.github.wingadium.search.ui.adapter.models.QuestionModel;
import com.github.wingadium.search.ui.common.CharacterProcessing;
import com.github.wingadium.search.ui.common.Constant;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String TAG = MainFragment.class.getName();


    private String mFilePath;
    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;
    private List<QuestionModel> mModels;

    public MainFragment() {
    }

    public static ArrayList<String> getLines(String filename) throws Exception {
        ArrayList<String> lines = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(filename);

        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            lines.add(strLine);
        }
        fstream.close();
        return lines;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mFilePath = bundle.getString(Constant.ACTION_FILE_PATH);
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mModels = new ArrayList<QuestionModel>();

        try {
            for (String line : getLines(mFilePath)) {
                int pos = line.indexOf(Constant.STRING_VERTICAL_BAR);
                if (pos > 0) {
                    String quest = line.substring(0, pos - 1);
                    String answer = line.substring(pos + 1);
                    mModels.add(new QuestionModel(quest, answer));
                }
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        mAdapter = new QuestionAdapter(getActivity(), mModels);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        final List<QuestionModel> filteredModelList = filter(mModels, query);
        mAdapter.animateTo(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    private List<QuestionModel> filter(List<QuestionModel> models, String query) {
        query = query.toLowerCase().replaceAll(Constant.STRING_REGEX, Constant.STRING_EMPTY);
        query = CharacterProcessing.remove_unicode(query);

        final List<QuestionModel> filteredModelList = new ArrayList<QuestionModel>();
        for (QuestionModel model : models) {
            String text = model.getQuestion().toLowerCase().replaceAll(Constant.STRING_REGEX, Constant.STRING_EMPTY);
            text = CharacterProcessing.remove_unicode(text);
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
