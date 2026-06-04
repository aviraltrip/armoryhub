package com.armoury;

import com.armoury.model.Weapon;
import com.armoury.service.WeaponService;
import com.armoury.service.IssuanceService;
import com.armoury.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ArmourySystem {

    private static ArmourySystem instance;

    private final WeaponService weaponService;
    private final IssuanceService issuanceService;
    private final ReturnService returnService;

    @Autowired
    public ArmourySystem(WeaponService weaponService, IssuanceService issuanceService, ReturnService returnService) {
        this.weaponService = weaponService;
        this.issuanceService = issuanceService;
        this.returnService = returnService;
    }

    @PostConstruct
    public void init() {
        instance = this;
    }

    public static ArmourySystem getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ArmourySystem has not been initialized by the Spring context.");
        }
        return instance;
    }

    public WeaponService getWeaponService() {
        return weaponService;
    }

    public IssuanceService getIssuanceService() {
        return issuanceService;
    }

    public ReturnService getReturnService() {
        return returnService;
    }
}
