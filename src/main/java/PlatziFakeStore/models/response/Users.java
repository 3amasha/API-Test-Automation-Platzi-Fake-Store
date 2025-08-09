package PlatziFakeStore.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users {

        private Integer id;
        private String email;
        private String password;
        private String name;
        private String role;
        private String avatar;
        private String creationAt;
        private String updatedAt;

        public Users() {
            // Default constructor
        }
        public Users(Integer id, String email, String password, String name, String role, String avatar, String creationAt, String updatedAt) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.name = name;
            this.role = role;
            this.avatar = avatar;
            this.creationAt = creationAt;
            this.updatedAt = updatedAt;
        }
        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }

        public String getAvatar() {
            return avatar;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreationAt() {
            return creationAt;
        }
        public void setCreationAt(String creationAt) {
            this.creationAt = creationAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

}
