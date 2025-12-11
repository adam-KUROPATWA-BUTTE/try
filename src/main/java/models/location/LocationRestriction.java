package models.location;

import models.people.Character;

public final class LocationRestriction {

    private LocationRestriction() {}


/**
 * 
 * In this method, we check if a character is allowed in a specific location type based on their affiliation.
 * @param type The type of location.
 * @param character The character to check.
 * @return true if the character is allowed in the location, false otherwise.
 * 
 */

    public static boolean isAllowed(LocationType type, Character<?, ?> character) {
        if (character == null) return false;
        if (type == LocationType.BATTLEFIELD) return true; // everyone is allowed

        // Detection via instanceof if interfaces/classes exist (compile-time safe
        // if you explicitly import) — here we do generic detection via reflection.
        boolean isRoman = implementsInterfaceNamed(character, "Roman");
        boolean isGaulois = implementsInterfaceNamed(character, "Gaulois") ||
                character.getClass().getSimpleName().toLowerCase().contains("gaul") ||
                character.getClass().getSimpleName().toLowerCase().contains("gaulois");
        boolean isFantastique = implementsInterfaceNamed(character, "Lycanthrope") ||
                implementsInterfaceNamed(character, "Fantastique") ||
                character.getClass().getSimpleName().toLowerCase().contains("lycanth") ||
                character.getClass().getSimpleName().toLowerCase().contains("werewolf") ||
                character.getClass().getSimpleName().toLowerCase().contains("fantast");

        return switch (type) {
            case GAUL_TOWN -> isGaulois || isFantastique;
            case ROMAIN_CAMP, ROMAIN_TOWN -> isRoman || isFantastique;
            case GAUL_ROMAIN_VILLAGE -> isGaulois || isRoman;
            case ENCLOSURE -> isFantastique;
            case BATTLEFIELD -> true;
            default -> false;
        };
    }

/**
 * 
 * Checks if an object implements an interface with the given simple name.
 * @param obj The object to check.
 * @param ifaceSimpleName The simple name of the interface.
 * @return true if the object implements the interface, false otherwise.
 * 
 */

    private static boolean implementsInterfaceNamed(Object obj, String ifaceSimpleName) {
        Class<?> cls = obj.getClass();
        while (cls != null && cls != Object.class) {
            for (Class<?> iface : cls.getInterfaces()) {
                if (iface.getSimpleName().equalsIgnoreCase(ifaceSimpleName)) {
                    return true;
                }
                if (implementsInterfaceNamedRecursive(iface, ifaceSimpleName)) {
                    return true;
                }
            }
            cls = cls.getSuperclass();
        }
        return false;
    }

/**
 * 
 * Helpers method to recursively check parent interfaces.
 * @param iface The interface to check.
 * @param ifaceSimpleName The simple name of the interface.
 * @return true if the interface or any of its parents match the name, false otherwise.
 * 
 */

    private static boolean implementsInterfaceNamedRecursive(Class<?> iface, String ifaceSimpleName) {
        for (Class<?> parent : iface.getInterfaces()) {
            if (parent.getSimpleName().equalsIgnoreCase(ifaceSimpleName)) {
                return true;
            }
            if (implementsInterfaceNamedRecursive(parent, ifaceSimpleName)) {
                return true;
            }
        }
        return false;
    }
}