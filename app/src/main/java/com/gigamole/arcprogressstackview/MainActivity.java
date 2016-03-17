package com.gigamole.arcprogressstackview;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.commonsware.cwac.colormixer.ColorMixer;
import com.commonsware.cwac.colormixer.ColorMixerDialog;
import com.gigamole.library.ArcProgressStackView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,
        CompoundButton.OnCheckedChangeListener,
        SeekBar.OnSeekBarChangeListener {

    public final static int MODEL_COUNT = 4;

    private ArcProgressStackView mArcProgressStackView;

    private Button mBtnShadowColor;
    private Button mBtnTextColor;

    private int[] mStartColors = new int[MODEL_COUNT];
    private int[] mEndColors = new int[MODEL_COUNT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mArcProgressStackView = (ArcProgressStackView) findViewById(R.id.apsv);

        final String[] startColors = getResources().getStringArray(R.array.polluted_waves);
        final String[] endColors = getResources().getStringArray(R.array.default_preview);
        final String[] bgColors = getResources().getStringArray(R.array.medical_express);

        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }

        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("Circle", 0, Color.parseColor(bgColors[0]), mStartColors[0]));
        models.add(new ArcProgressStackView.Model("Progress", 0, Color.parseColor(bgColors[1]), mStartColors[1]));
        models.add(new ArcProgressStackView.Model("Stack", 0, Color.parseColor(bgColors[2]), mStartColors[2]));
        models.add(new ArcProgressStackView.Model("View", 0, Color.parseColor(bgColors[3]), mStartColors[3]));
        mArcProgressStackView.setModels(models);

        final CheckBox cbAnimating = (CheckBox) findViewById(R.id.cb_animating);
        final CheckBox cbDragging = (CheckBox) findViewById(R.id.cb_dragging);
        final CheckBox cbShadowing = (CheckBox) findViewById(R.id.cb_shadowing);
        final CheckBox cbRounding = (CheckBox) findViewById(R.id.cb_rounding);
        final CheckBox cbShowModelBg = (CheckBox) findViewById(R.id.cb_show_model_bg);
        final CheckBox cbUseCustomTypeface = (CheckBox) findViewById(R.id.cb_use_custom_typeface);
        final CheckBox cbUseOvershootInterpolator = (CheckBox) findViewById(R.id.cb_use_overshoot_interpolator);
        final CheckBox cbUseVerticalOrientation = (CheckBox) findViewById(R.id.cb_use_vertical_orientation);
        final CheckBox cbUseGradient = (CheckBox) findViewById(R.id.cb_use_gradient);

        cbAnimating.setOnCheckedChangeListener(this);
        cbDragging.setOnCheckedChangeListener(this);
        cbShadowing.setOnCheckedChangeListener(this);
        cbRounding.setOnCheckedChangeListener(this);
        cbShowModelBg.setOnCheckedChangeListener(this);
        cbUseCustomTypeface.setOnCheckedChangeListener(this);
        cbUseOvershootInterpolator.setOnCheckedChangeListener(this);
        cbUseVerticalOrientation.setOnCheckedChangeListener(this);
        cbUseGradient.setOnCheckedChangeListener(this);

        onCheckedChanged(cbAnimating, cbAnimating.isChecked());
        onCheckedChanged(cbDragging, cbDragging.isChecked());
        onCheckedChanged(cbShadowing, cbShadowing.isChecked());
        onCheckedChanged(cbRounding, cbRounding.isChecked());
        onCheckedChanged(cbShowModelBg, cbShowModelBg.isChecked());
        onCheckedChanged(cbUseCustomTypeface, cbUseCustomTypeface.isChecked());
        onCheckedChanged(cbUseOvershootInterpolator, cbUseOvershootInterpolator.isChecked());
        onCheckedChanged(cbUseVerticalOrientation, cbUseVerticalOrientation.isChecked());
        onCheckedChanged(cbUseGradient, cbUseGradient.isChecked());

        mBtnTextColor = (Button) findViewById(R.id.btn_text_color);
        mBtnShadowColor = (Button) findViewById(R.id.btn_shadow_color);
        final Button btnAnimate = (Button) findViewById(R.id.btn_animate);
        final Button btnReset = (Button) findViewById(R.id.btn_reset);

        mBtnTextColor.setOnClickListener(this);
        mBtnShadowColor.setOnClickListener(this);
        btnAnimate.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        handleSelectedColor(true, Color.DKGRAY);
        handleSelectedColor(false, Color.WHITE);

        final SeekBar sbShadowDistance = (SeekBar) findViewById(R.id.pb_shadow_distance);
        final SeekBar sbShadowAngle = (SeekBar) findViewById(R.id.pb_shadow_angle);
        final SeekBar sbShadowRadius = (SeekBar) findViewById(R.id.pb_shadow_radius);
        final SeekBar sbAnimationDuration = (SeekBar) findViewById(R.id.pb_animation_duration);
        final SeekBar sbDrawWidth = (SeekBar) findViewById(R.id.pb_draw_width);
        final SeekBar sbModelOffset = (SeekBar) findViewById(R.id.pb_model_offset);
        final SeekBar sbStartAngle = (SeekBar) findViewById(R.id.pb_start_angle);
        final SeekBar sbSweepAngle = (SeekBar) findViewById(R.id.pb_sweep_angle);

        sbShadowDistance.setOnSeekBarChangeListener(this);
        sbShadowAngle.setOnSeekBarChangeListener(this);
        sbShadowRadius.setOnSeekBarChangeListener(this);
        sbAnimationDuration.setOnSeekBarChangeListener(this);
        sbDrawWidth.setOnSeekBarChangeListener(this);
        sbModelOffset.setOnSeekBarChangeListener(this);
        sbStartAngle.setOnSeekBarChangeListener(this);
        sbSweepAngle.setOnSeekBarChangeListener(this);

        mArcProgressStackView.setAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(final Animator animation) {

            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                Toast.makeText(MainActivity.this, "ANIMATION", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(final Animator animation) {

            }

            @Override
            public void onAnimationRepeat(final Animator animation) {

            }
        });
        mArcProgressStackView.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnAnimate.performClick();
            }
        }, 333);
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_animating:
                mArcProgressStackView.setIsAnimated(isChecked);
                break;
            case R.id.cb_dragging:
                mArcProgressStackView.setIsDragged(isChecked);
                break;
            case R.id.cb_shadowing:
                mArcProgressStackView.setIsShadowed(isChecked);
                break;
            case R.id.cb_rounding:
                mArcProgressStackView.setIsRounded(isChecked);
                break;
            case R.id.cb_show_model_bg:
                mArcProgressStackView.setModelBgEnabled(isChecked);
                break;
            case R.id.cb_use_custom_typeface:
                mArcProgressStackView.setTypeface(isChecked ? "fonts/agency.ttf" : "");
                break;
            case R.id.cb_use_overshoot_interpolator:
                mArcProgressStackView.setInterpolator(isChecked ? new OvershootInterpolator() : null);
                break;
            case R.id.cb_use_vertical_orientation:
                mArcProgressStackView.setIndicatorOrientation(isChecked ?
                                ArcProgressStackView.IndicatorOrientation.VERTICAL :
                                ArcProgressStackView.IndicatorOrientation.HORIZONTAL
                );
                break;
            case R.id.cb_use_gradient:
                for (int i = 0; i < mArcProgressStackView.getModels().size(); i++) {
                    final ArcProgressStackView.Model model = mArcProgressStackView.getModels().get(i);
                    model.setColor(mStartColors[i]);
                    model.setColors(isChecked ? new int[]{mStartColors[i], mEndColors[i]} : null);
                }
                mArcProgressStackView.requestLayout();
                break;
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.btn_text_color:
                showColorPicker(false);
                break;
            case R.id.btn_shadow_color:
                showColorPicker(true);
                break;
            case R.id.btn_animate:
                final Random random = new Random();
                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels())
                    model.setProgress(random.nextInt(101));
                mArcProgressStackView.animateProgress();
                break;
            case R.id.btn_reset:
                finish();
                startActivity(getIntent());
                break;
        }
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, int progress, final boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.pb_shadow_distance:
                mArcProgressStackView.setShadowDistance(progress);
                break;
            case R.id.pb_shadow_angle:
                mArcProgressStackView.setShadowAngle(progress);
                break;
            case R.id.pb_shadow_radius:
                mArcProgressStackView.setShadowRadius(progress);
                break;
            case R.id.pb_animation_duration:
                mArcProgressStackView.setAnimationDuration(progress);
                break;
            case R.id.pb_draw_width:
                mArcProgressStackView.setDrawWidthFraction((float) progress / 100.0f);
                break;
            case R.id.pb_model_offset:
                progress = progress - 50;
                mArcProgressStackView.setProgressModelOffset(progress);
                break;
            case R.id.pb_start_angle:
                mArcProgressStackView.setStartAngle(progress);
                break;
            case R.id.pb_sweep_angle:
                mArcProgressStackView.setSweepAngle(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {

    }

    private void showColorPicker(final boolean isShadowColor) {
        new ColorMixerDialog(
                this,
                isShadowColor ? mBtnShadowColor.getCurrentTextColor() : mBtnTextColor.getCurrentTextColor(),
                new ColorMixer.OnColorChangedListener() {
                    @Override
                    public void onColorChange(final int i) {
                        handleSelectedColor(isShadowColor, i);
                    }
                }).show();
    }

    private void handleSelectedColor(final boolean isShadowColor, final int selectedColor) {
        if (isShadowColor) {
            mArcProgressStackView.setShadowColor(selectedColor);
            mBtnShadowColor.setTextColor(selectedColor);
        } else {
            mArcProgressStackView.setTextColor(selectedColor);
            mBtnTextColor.setTextColor(selectedColor);
        }
    }
}