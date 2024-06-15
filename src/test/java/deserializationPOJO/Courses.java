package deserializationPOJO;

import java.util.List;

public class Courses {

    private List<webAutomation> webAutomation;
    private List<api> api;
    private List<mobile> mobile;


    public List<deserializationPOJO.webAutomation> getWebAutomation() {
        return webAutomation;
    }

    public void setWebAutomation(List<deserializationPOJO.webAutomation> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public List<deserializationPOJO.api> getApi() {
        return api;
    }

    public void setApi(List<deserializationPOJO.api> api) {
        this.api = api;
    }

    public List<deserializationPOJO.mobile> getMobile() {
        return mobile;
    }

    public void setMobile(List<deserializationPOJO.mobile> mobile) {
        this.mobile = mobile;
    }







}
