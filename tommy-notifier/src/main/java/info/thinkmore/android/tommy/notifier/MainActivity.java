package info.thinkmore.android.tommy.notifier;

import android.app.Activity;
import android.view.Menu;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity
    extends Activity
{

    @AfterViews
    void afterViews() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater();
        return true;
    }

}
