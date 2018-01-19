package app.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@NoArgsConstructor
@Data
public class ContextService {
    private String clientId;
    private String clientName;
    private String token;
    private String nbr;
}
