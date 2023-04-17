package lk.ijse.oniessoftware.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class CustomerDTO {
  private  String cust_Id;
  private   String name;
  private  String c_Tp;
  private  String user_Id;
}
 /*   public CustomerDTO(String cust_Id, String name, String c_Tp, String user_Id) {
        this.cust_Id = cust_Id;
        this.name = name;
        this.c_Tp = c_Tp;
        this.user_Id = user_Id;
    }

    public CustomerDTO() {
    }




    public String getCust_Id() {
        return cust_Id;
    }

    public void setCust_Id(String cust_Id) {
        this.cust_Id = cust_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getC_Tp() {
        return c_Tp;
    }

    public void setC_Tp(String c_Tp) {
        this.c_Tp = c_Tp;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }


    @Override
    public String toString() {
        return "CustomerDTO{" +
                "cust_Id='" + cust_Id + '\'' +
                ", name='" + name + '\'' +
                ", c_Tp='" + c_Tp + '\'' +
                ", user_Id='" + user_Id + '\'' +
                '}';
    }

}*/
