/**
 * The crime date object, used to filter date range
 *
 * @author Nick Ma, haoxuanm
 */

public class CrimeDate implements Comparable<CrimeDate> {
    int day, month, year;

    //constructor
    public CrimeDate(String date) {
        String[] nums = date.split("/");
        month = Integer.parseInt(nums[0]);
        day = Integer.parseInt(nums[1]);
        year = Integer.parseInt(nums[2]);
    }

    //getters and setters
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //comparator
    @Override
    public int compareTo(CrimeDate o) {
        if (this.year < o.year) {
            return -1;
        } else if (this.year == o.year) {
            if (this.month == o.month) {
                if (this.day == o.day) {
                    return 0;
                } else if (this.day > o.day) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (this.month > o.month) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }

    public String toString() {
        return month + "/" + day + "/" + year;
    }

}
