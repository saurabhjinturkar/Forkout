package com.example.paypro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.paypro.data.User;
import com.example.paypro.dataprovider.FriendDetailAdapter;

/**
 * @author jintu
 * 
 */
public class FriendsFragment extends Fragment {

	private FriendDetailAdapter adapter;

	public FriendsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_group_member,
				container, false);
		ListView list = (ListView) rootView.findViewById(R.id.group_memberList);
		adapter = new FriendDetailAdapter(getActivity().getApplicationContext());
		list.setAdapter(adapter);
		adapter.fetchData();
		return rootView;
	}

}
