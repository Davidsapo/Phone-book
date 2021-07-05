import controller.PhoneBookController;
import view.PhoneBookView;

public class PhoneBook {

    public PhoneBook() {
        new PhoneBookView(new PhoneBookController()).setVisible(true);
    }
}
