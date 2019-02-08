/**
 * TitleFragment.java
 *
 * Project #3, A2
 *
 * Author: Alex Viznytsya
 *
 * CS 478 Software Development for Mobile Platforms
 * Spring 2018, UIC
 *
 * Date: 04/02/2018
 */

package edu.uic.cs478.sp18.project3.a2;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TitleFragment extends ListFragment {

    private ListSelectionListener activityLisrener = null;
    private String[] titleArray;

    public interface ListSelectionListener {
        public void onListSelection(int index);
        public String[] getTitleArray();
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            activityLisrener = (ListSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
        if(titleArray == null) {
            titleArray = activityLisrener.getTitleArray();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.title_item, titleArray));
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        getListView().setItemChecked(pos, true);
        activityLisrener.onListSelection(pos);
    }

}
