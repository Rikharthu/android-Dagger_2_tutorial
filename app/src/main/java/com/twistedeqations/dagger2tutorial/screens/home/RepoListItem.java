package com.twistedeqations.dagger2tutorial.screens.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twistedeqations.dagger2tutorial.R;
import com.twistedeqations.dagger2tutorial.models.GithubRepo;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListItem extends FrameLayout {

    private final Picasso mPicasso;

    @BindView(R.id.user_avatar_image)
    ImageView avatar;

    @BindView(R.id.repo_name)
    TextView name;

    @BindView(R.id.repo_description)
    TextView description;

    @BindView(R.id.repo_stars)
    TextView stars;

    @BindView(R.id.repo_issues)
    TextView issues;

    @BindView(R.id.repo_forks)
    TextView forks;

    @BindView(R.id.repo_updated_at)
    TextView updatedAt;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.fullDate();

    public RepoListItem(Context context, Picasso picasso) {
        super(context);
        mPicasso = picasso;
        inflate(getContext(), R.layout.repo_list_item, this);
        ButterKnife.bind(this);
    }

    public void setRepo(GithubRepo githubRepo) {
        Locale locale = getResources().getConfiguration().locale;

        name.setText(githubRepo.name);
        description.setText(githubRepo.description);

        stars.setText(String.format(locale, "%d", githubRepo.stargazersCount));
        issues.setText(String.format(locale, "%d", githubRepo.openIssuesCount));
        forks.setText(String.format(locale, "%d", githubRepo.forksCount));

        updatedAt.setText(DATE_TIME_FORMATTER.print(githubRepo.updatedAt));

        mPicasso.load(githubRepo.owner.avatarUrl)
                .fit()
                .into(avatar);
    }
}
