package fr.polytech.codev.backend.services.impl;

import fr.polytech.codev.backend.entities.Device;
import fr.polytech.codev.backend.entities.User;
import fr.polytech.codev.backend.exceptions.InvalidEntityException;
import fr.polytech.codev.backend.exceptions.UnknownEntityException;
import fr.polytech.codev.backend.forms.DeviceForm;
import fr.polytech.codev.backend.repositories.DaoRepository;
import fr.polytech.codev.backend.services.AbstractServices;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceServices extends AbstractServices {

    @Autowired
    private DaoRepository<Device> deviceDaoRepository;

    @Autowired
    private DaoRepository<User> userDaoRepository;

    public List<Device> all() throws UnknownEntityException {
        final List<Device> devices = this.deviceDaoRepository.getAll();
        if (devices == null) {
            throw new UnknownEntityException();
        }

        return devices;
    }

    public Device get(int id) throws UnknownEntityException {
        final Device device = this.deviceDaoRepository.get(id);
        if (device == null) {
            throw new UnknownEntityException();
        }

        return device;
    }

    public List<Device> getByUuid(String value) throws UnknownEntityException {
        final Map<String, Serializable> parameters = new HashMap<String, Serializable>();
        parameters.put("uuid", value);

        final List<Device> devices = this.deviceDaoRepository.filter(parameters);
        if (devices == null) {
            throw new UnknownEntityException();
        }

        return devices;
    }

    public Device insert(DeviceForm deviceForm) throws InvalidEntityException {
        final Device device = new Device();
        device.setPlatform(deviceForm.getPlatform());
        device.setUuid(deviceForm.getUuid());
        device.setCreationDate(LocalDateTime.now());
        device.setLastUpdate(LocalDateTime.now());
        device.setUser(this.userDaoRepository.get(deviceForm.getUserId()));

        validate(device);
        this.deviceDaoRepository.insert(device);

        return device;
    }

    public Device update(int id, DeviceForm deviceForm) throws UnknownEntityException, InvalidEntityException {
        final Device device = get(id);
        device.setPlatform(deviceForm.getPlatform());
        device.setUuid(deviceForm.getUuid());
        device.setLastUpdate(LocalDateTime.now());

        validate(device);
        this.deviceDaoRepository.update(device);

        return device;
    }

    public void delete(int id) throws UnknownEntityException {
        this.deviceDaoRepository.delete(get(id));
    }
}