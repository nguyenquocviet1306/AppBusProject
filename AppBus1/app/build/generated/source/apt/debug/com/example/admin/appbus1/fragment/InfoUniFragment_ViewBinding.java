// Generated code from Butter Knife. Do not modify!
package com.example.admin.appbus1.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.admin.appbus1.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InfoUniFragment_ViewBinding<T extends InfoUniFragment> implements Unbinder {
  protected T target;

  @UiThread
  public InfoUniFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.tv_name = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tv_name'", TextView.class);
    target.tv_address = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tv_address'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.tv_name = null;
    target.tv_address = null;

    this.target = null;
  }
}
