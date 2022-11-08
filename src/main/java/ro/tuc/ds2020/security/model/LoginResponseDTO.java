package ro.tuc.ds2020.security.model;

public class LoginResponseDTO {
    private Long id;
    private String accessToken;

    public LoginResponseDTO() { }

    public LoginResponseDTO(Long id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
