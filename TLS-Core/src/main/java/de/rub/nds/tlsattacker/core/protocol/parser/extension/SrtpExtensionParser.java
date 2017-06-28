/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.parser.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.ExtensionByteLength;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SrtpExtensionMessage;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class SrtpExtensionParser extends ExtensionParser<SrtpExtensionMessage> {

    public SrtpExtensionParser(int startposition, byte[] array) {
        super(startposition, array);
    }

    @Override
    public void parseExtensionMessageContent(SrtpExtensionMessage msg) {
        msg.setSrtpProtectionProfilesLength(parseIntField(ExtensionByteLength.SRTP_PROTECTION_PROFILES_LENGTHFIELD_LENGTH));
        LOGGER.debug("The srtp extension parser parsed the srtp protection profiles length of "
                + msg.getSrtpProtectionProfilesLength().getValue());
        msg.setSrtpProtectionProfiles(parseByteArrayField(msg.getSrtpProtectionProfilesLength().getValue()));
        LOGGER.debug("The srtp extension parser parsed the srtp protection profiles "
                + ArrayConverter.bytesToHexString(msg.getSrtpProtectionProfiles()));
        msg.setSrtpMkiLength(parseIntField(ExtensionByteLength.SRTP_MASTER_KEY_IDENTIFIER_LENGTHFIELD_LENGTH));
        LOGGER.debug("The srtp extension parser parsed the srtp mki length of " + msg.getSrtpMkiLength().getValue());
        if (msg.getSrtpMkiLength().getValue() != 0) {
            msg.setSrtpMki(parseByteArrayField(msg.getSrtpMkiLength().getValue()));
            LOGGER.debug("The srtp extension parser parsed the srtp mki "
                    + ArrayConverter.bytesToHexString(msg.getSrtpMki()));
        } else {
            msg.setSrtpMki(new byte[] {});
            LOGGER.debug("The srtp extension parser parsed no mki");
        }

    }

    @Override
    protected SrtpExtensionMessage createExtensionMessage() {
        return new SrtpExtensionMessage();
    }

}
