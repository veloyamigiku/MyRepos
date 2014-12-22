package jp.co.myself.TabNaviTemplate.fragment;

import jp.co.myself.TabNaviTemplate.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SecondTabFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_second_tab, container, false);
	}
	
}
