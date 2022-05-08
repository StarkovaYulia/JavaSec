package ru.tsu.hits.springdb2;


import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Collection;
import java.util.Date;

@ToString
@Getter
@Setter

public class TaskCsv {

    @CsvBindByPosition(position = 0)
    private String fio;

    @CsvBindByPosition(position = 1)
    private String email;

    @CsvBindByPosition(position = 2)
    private String password;

    @CsvBindByPosition(position = 3)
    private String role;

    @CsvBindByPosition(position = 4)
    private String creationDate;

    @CsvBindByPosition(position = 5)
    private String editDate;


    public String getField(String fieldName)
    {
        String field = null;
        switch (fieldName)
        {
            case "fio":
                field = this.getFio();
                break;
            case "email":
                field = this.getEmail();
                break;
            case "password":
                field = this.getPassword();
                break;
            case "role":
                field = this.getRole();
                break;
            case "creationDate":
                field = this.getCreationDate();
                break;
            case "editDate":
                field = this.getEditDate();
                break;
            default:
                field = "It is illegal argument";
                break;
        }
        return field;
    }


}