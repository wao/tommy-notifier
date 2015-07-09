package info.thinkmore.android.tommy.notifier;

import android.app.Activity;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;


@Ignore
@RunWith(RobolectricTestRunner.class)
@Config(manifest="./AndroidManifest.xml",emulateSdk=18)
public class MainActivityTest {

  @org.junit.Test
  public void titleIsCorrect() throws Exception {
    Activity activity = Robolectric.setupActivity(MainActivity_.class);
    assertTrue(activity.getTitle().toString().equals("notifier"));
  }
}
