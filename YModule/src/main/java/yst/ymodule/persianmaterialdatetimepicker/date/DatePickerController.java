
package yst.ymodule.persianmaterialdatetimepicker.date;


import yst.ymodule.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * Controller class to communicate among the various components of the date picker dialog.
 */
public interface DatePickerController {

    void onYearSelected(int year);

    void onDayOfMonthSelected(int year, int month, int day);

    void registerOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener);

    void unregisterOnDateChangedListener(DatePickerDialog.OnDateChangedListener listener);

    MonthAdapter.CalendarDay getSelectedDay();

    boolean isThemeDark();

    PersianCalendar[] getHighlightedDays();

    PersianCalendar[] getSelectableDays();

    int getFirstDayOfWeek();

    int getMinYear();

    int getMaxYear();

    PersianCalendar getMinDate();

    PersianCalendar getMaxDate();

    void tryVibrate();

    void setTypeface(String fontName);

    String getTypeface();
}
