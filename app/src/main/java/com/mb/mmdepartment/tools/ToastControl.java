package com.mb.mmdepartment.tools;
/**
 * toast对话框
 */
public interface ToastControl {
    /**
     * 资源类型的
     * @param message
     */
    public abstract void showToast(int message);

    /**
     * 纯文字的
     * @param message
     */
    public abstract void showToast(String message);

    /**
     * 带有图片和资源的
     * @param message
     * @param icon
     */
    public abstract void showToast(int message, int icon);

    /**
     * 文字和图片的
     * @param message
     * @param icon
     */
    public abstract void showToast(String message, int icon);

    /**
     * 短时间的toast带资源的
     * @param message
     */
    public abstract void showToastShort(int message);

    /**
     * 短时间的toast带文字的
     * @param message
     */
    public abstract void showToastShort(String message);

    /**
     * 短时间的toast带文字和多类型的
     * @param message
     * @param args
     */
    public abstract void showToastShort(int message, Object... args);

    /**
     * 带有时间类型的toast
     * @param message
     * @param duration
     * @param icon
     */
    public abstract void showToast(int message, int duration, int icon);

    /**
     * 带有时间和位置的toast
     * @param message
     * @param duration
     * @param icon
     * @param gravity
     */
    public abstract void showToast(int message, int duration, int icon,
                                   int gravity);

    public abstract void showToast(int message, int duration, int icon,
                                   int gravity, Object... args);

    public abstract void showToast(String message, int duration, int icon,
                                   int gravity);
}
