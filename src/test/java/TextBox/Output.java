package TextBox;

public class Output {
    private final String name;
    private final String email;
    private final String currentAddress;
    private final String permanentAddress;

    public Output(String name, String email, String currentAddress, String permanentAddress) {
        this.name = name;
        this.email = email;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }
}
