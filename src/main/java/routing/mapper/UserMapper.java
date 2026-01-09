package routing.mapper;

import org.mapstruct.Mapper;
import routing.dto.TgRegistrationDTO;
import routing.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserFromRegistration(TgRegistrationDTO tgRegistrationDTO);
}
