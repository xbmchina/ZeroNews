package ui.fragment;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import ui.view.NoScrollViewPager;
import com.example.zero.zeroapp.R;

import java.util.ArrayList;

import base.BaseFragment;
import base.BasePager;
import pager.GovAffairsPager;
import pager.HomePager;
import pager.NewsCenterPager;
import pager.SettingPager;
import pager.SmartServicePager;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ContentFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private RadioGroup rgGroup;


    private ArrayList<BasePager> mPagers;// 五个标签页的集合

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        mViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_content);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<BasePager>();

        // 添加五个标签页
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsCenterPager(mActivity));
        mPagers.add(new SmartServicePager(mActivity));
        mPagers.add(new GovAffairsPager(mActivity));
        mPagers.add(new SettingPager(mActivity));

        mViewPager.setAdapter(new ContentAdapter());


        // 底栏标签切换监听
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        // 首页
                        // mViewPager.setCurrentItem(0);
                        mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画
                        break;
                    case R.id.rb_news:
                        // 新闻中心
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        // 智慧服务
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        // 政务
                        mViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        // 设置
                        mViewPager.setCurrentItem(4, false);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            View view = pager.mRootView;// 获取当前页面对象的布局

             pager.initData();// 初始化数据, viewpager会默认加载下一个页面,
            // 为了节省流量和性能,不要在此处调用初始化数据的方法

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
}