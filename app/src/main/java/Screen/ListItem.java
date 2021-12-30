package Screen;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

public class ListItem extends BaseObservable {
    private String patientName;
    private String patientPhone;
    private String name;
    private int age;
    private int photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    /*参数变更监听*/
    @Override
    public void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        super.addOnPropertyChangedCallback(callback);
    }
}
