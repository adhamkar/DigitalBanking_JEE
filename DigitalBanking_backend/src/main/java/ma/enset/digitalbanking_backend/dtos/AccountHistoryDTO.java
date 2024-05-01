package ma.enset.digitalbanking_backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int TotalPages;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationDTOS;
}
