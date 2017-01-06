// Generated code from Butter Knife. Do not modify!
package com.example.admin.appbus1.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.admin.appbus1.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListUniFragment_ViewBinding<T extends ListUniFragment> implements Unbinder {
  protected T target;

  @UiThread
  public ListUniFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.rv_university = Utils.findRequiredViewAsType(source, R.id.rv_university, "field 'rv_university'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rv_university = null;

    this.target = null;
  }
}
