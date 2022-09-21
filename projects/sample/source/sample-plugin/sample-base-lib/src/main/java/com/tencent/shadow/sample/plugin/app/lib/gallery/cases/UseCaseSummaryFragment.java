/*
 * Tencent is pleased to support the open source community by making Tencent Shadow available.
 * Copyright (C) 2019 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.tencent.shadow.sample.plugin.app.lib.gallery.cases;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.shadow.sample.plugin.app.lib.base.R;
import com.tencent.shadow.sample.plugin.app.lib.gallery.cases.entity.UseCase;
import com.tencent.shadow.sample.plugin.app.lib.gallery.util.PluginChecker;

public class UseCaseSummaryFragment extends Fragment {

    private TextView mCaseName;
    private Button mStartCase;
    private TextView mCaseSummary;
    private TextView mEnvironment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_case_summary, container, false);
        bindViews(view);
        return view;
    }


    public void setCase(final UseCase useCase) {
        mCaseName.setText(useCase.getName());
        mCaseSummary.setText(useCase.getSummary());
        mStartCase.setVisibility(View.VISIBLE);

        mStartCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), useCase.getPageClass());
                if (useCase.getPageParams() != null) {
                    intent.putExtras(useCase.getPageParams());
                }
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //只在API 21以上手工测试一下ActivityOptions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(
                            getActivity(),
                            android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right
                    );

                    //测试调用makeSceneTransitionAnimation方法传入Activity

                    ActivityOptions.makeSceneTransitionAnimation(
                            getActivity(),
                            UseCaseSummaryFragment.this.mCaseName,
                            "mCaseName"
                    );

                    //测试调用makeSceneTransitionAnimation方法传入Activity
                    ActivityOptions.makeSceneTransitionAnimation(
                            getActivity(),
                            new Pair<>(UseCaseSummaryFragment.this.mCaseName, "mCaseName")
                    );

                    startActivity(intent,
                            activityOptions.toBundle()
                    );
                } else {
                    startActivity(intent);
                }
            }
        });
    }


    private void bindViews(View view) {
        mCaseName = (TextView) view.findViewById(R.id.case_name);
        mStartCase = (Button) view.findViewById(R.id.start_case);
        mCaseSummary = (TextView) view.findViewById(R.id.case_summary);
        mEnvironment = (TextView) view.findViewById(R.id.environment);
        view.findViewById(R.id.test_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity(),"com.tencent.shadow.sample.plugin.app.message");
                mBuilder.setContentTitle("我是标题")
                        .setContentText("我是内容")
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.collapse_test))
                        .setSmallIcon(R.drawable.collapse_test)
                        .setWhen(System.currentTimeMillis())
                        .setTicker("我是测试内容")
                        .setDefaults(Notification.DEFAULT_SOUND);
                notificationManager.notify(10, mBuilder.build());
            }
        });

        mEnvironment.setText(PluginChecker.isPluginMode() ? "当前环境：插件模式" : "当前环境：独立安装");
    }

}
