package yunshuo.yneb.com.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import yunshuo.yneb.com.myapplication.Constant;
import yunshuo.yneb.com.myapplication.GroupAdapter;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.activity.chat.ChatActivity;
import yunshuo.yneb.com.myapplication.other.utils.StringUtils;
import yunshuo.yneb.com.myapplication.other.utils.ToastUtils;

/**
 * Created by wangyang on 2016/12/14 0014.
 */

public class GroupFragment extends Fragment {
    private ListView groupListView;
    protected List<EMGroup> grouplist;
    private GroupAdapter groupAdapter;
    private InputMethodManager inputMethodManager;
    private View progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    refresh();
                    break;
                case 1:
                    Toast.makeText(getContext(), R.string.Failed_to_get_group_chat_information, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };
    private View inflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.group_view, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupListView = (ListView) inflate.findViewById(R.id.list);
        //show group list
        groupAdapter = new GroupAdapter(getContext(), 1, grouplist);
        groupListView.setAdapter(groupAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
                R.color.holo_orange_light, R.color.holo_red_light);
        //pull down to refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshGroups();
            }
        });
        refreshGroups();
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    // create a new group
                    ToastUtils.showToast("create a new group");
                } else if (position == 2) {
                    // join a public group
                    ToastUtils.showToast("join a public group");

                } else {
                    // enter group chat
                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    // it is group chat
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                    String groupId = groupAdapter.getItem(position - 3).getGroupId();
                    intent.putExtra(IVariable.CHAT_ID,groupId);
                    if ( StringUtils.isEmpty(groupId))return;
                    startActivityForResult(intent, 0);
                }
            }

        });
        groupListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                    if (getActivity().getCurrentFocus() != null)
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }

    private void refreshGroups() {
        new Thread(){
            @Override
            public void run(){
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    handler.sendEmptyMessage(0);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
    private void refresh(){
        grouplist = EMClient.getInstance().groupManager().getAllGroups();
        groupAdapter = new GroupAdapter(getContext(), 1, grouplist);
        groupListView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }

}
