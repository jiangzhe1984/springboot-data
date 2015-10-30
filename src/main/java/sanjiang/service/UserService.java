package sanjiang.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import sanjiang.domain.User;
import sanjiang.model.NativeQueryModel;
import sanjiang.model.PageModel;
import sanjiang.repository.UserRepository;

import java.util.List;

/**
 * Created by jiangzhe on 15-10-28.
 */
@Service("userService")
public class UserService extends BaseService<User>{

    @Autowired
    private UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(Long id){
        return userRepository.findOne(id);
    }

}
