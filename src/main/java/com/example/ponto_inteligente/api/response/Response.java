package com.example.ponto_inteligente.api.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Response<T> {

    private T data;

    @Setter
    private List<String> errors;

    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<String>();
        }
        return errors;
    }

}