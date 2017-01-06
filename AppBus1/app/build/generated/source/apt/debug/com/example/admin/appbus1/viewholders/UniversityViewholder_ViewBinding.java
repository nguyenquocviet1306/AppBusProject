// Generated code from Butter Knife. Do not modify!
package com.example.admin.appbus1.viewholders;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.admin.appbus1.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UniversityViewholder_ViewBinding<T extends UniversityViewholder> implements Unbinder {
  protected T target;

  @UiThread
  public UniversityViewholder_ViewBinding(T target, View source) {
    this.target = target;

    target.tv_university = Utils.findRequiredViewAsType(source, R.id.tv_university, "field 'tv_university'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_university = null;

    this.target = null;
  }
}
