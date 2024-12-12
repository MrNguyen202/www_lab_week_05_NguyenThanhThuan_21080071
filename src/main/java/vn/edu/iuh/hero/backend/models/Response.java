/*
 * @ (#) Response.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.models;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private int code;
    private String message;
    private Object data;
}
