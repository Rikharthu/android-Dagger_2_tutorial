package com.twistedeqations.dagger2tutorial.screens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twistedeqations.dagger2tutorial.GithubApplication;
import com.twistedeqations.dagger2tutorial.R;
import com.twistedeqations.dagger2tutorial.components.DaggerHomeActivityComponent;
import com.twistedeqations.dagger2tutorial.components.HomeActivityComponent;
import com.twistedeqations.dagger2tutorial.models.GithubRepo;
import com.twistedeqations.dagger2tutorial.modules.HomeActivityModule;
import com.twistedeqations.dagger2tutorial.network.GithubService;
import com.twistedeqations.dagger2tutorial.screens.home.AdapterRepos;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.repo_home_list)
    ListView listView;

    @Inject
    GithubService githubService;
    @Inject
    Picasso mPicasso;

    Call<List<GithubRepo>> reposCall;

    // tell the Dagger to populate this field
    @Inject
    AdapterRepos adapterRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        // Replaced by HomeActivityComponent version
        //githubService = GithubApplication.get(this).getGithubService();
        // replaced by @Inject
        // mPicasso = GithubApplication.get(this).getPicasso();

        HomeActivityComponent component= DaggerHomeActivityComponent.builder()
                .homeActivityModule(new HomeActivityModule(this))
                .githubApplicationComponent(GithubApplication.get(this).getComponent()) // here we join components
                .build();

        // Inject something here
        component.injectHomeActivity(this);

        // old way
//        adapterRepos = new AdapterRepos(this, mPicasso);
        // replaced by @Inject
//        adapterRepos=component.getAdapterRepos();
        listView.setAdapter(adapterRepos);

        // replaced by @Inject
        // we can also retrieve GithubService through our HomeActivityComponent instead of Application
//        githubService = component.getGithubService();

        reposCall = githubService.getReposForUser("Rikharthu");
        reposCall.enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                adapterRepos.swapData(response.body());
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error getting repos " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reposCall != null) {
            reposCall.cancel();
        }
    }
}
