package application.view.login;

import application.dao.UserDao;
import application.model.auth.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
public class loginController {
    private UserDao userDao = new UserDao();

    @FXML
    private JFXTextField uname;
    @FXML
    private JFXPasswordField passwd;
    @FXML
    private Text txt_message;

    /**
     * 登录按钮监听
     * @param event
     */
    @FXML
    public void handleLoginButtonAction(ActionEvent event) {
        String username = uname.getText();
        String password = passwd.getText();
        if (username.isEmpty()){
            txt_message.setText("用户名不能为空！");
            return;
        }else if (password.isEmpty()){
            txt_message.setText("密码不能为空！");
            return;
        }
        if (userDao.searchUser(username)){
            if (userDao.checkPassword(username,password)){
                User user = new User();
                txt_message.setText("登录成功");
            }else {
                txt_message.setText("密码错误！");
            }
        }else {
            txt_message.setText("用户不存在！");
        }

    }

    /**
     * 注册按钮监听
     * @param event
     */
    @FXML
    public void HandleRegisterButtonAction(ActionEvent event) {
        closeStage();//关闭当前窗口
        loadRegister();//加载注册窗口
    }

    /**
     * 登录成功后关闭窗口
     */
    private void closeStage() {
        ((Stage) uname.getScene().getWindow()).close();
    }


    /**
     * 加载注册窗口
     */
    void loadRegister() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/application/view/register/register.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("图书进销存注册中心");
            stage.setScene(new Scene(parent));
            stage.show();
//            LibraryAssistantUtil.setStageIcon(stage);
        }
        catch (IOException ex) {
//            LOGGER.log(Level.ERROR, "{}", ex);
        }
    }

}

