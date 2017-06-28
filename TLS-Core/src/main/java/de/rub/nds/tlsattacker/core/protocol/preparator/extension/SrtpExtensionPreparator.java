/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.preparator.extension;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.SrtpProtectionProfiles;
import de.rub.nds.tlsattacker.core.protocol.message.extension.SrtpExtensionMessage;
import de.rub.nds.tlsattacker.core.workflow.TlsContext;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author Matthias Terlinde <matthias.terlinde@rub.de>
 */
public class SrtpExtensionPreparator extends ExtensionPreparator<SrtpExtensionMessage> {

    private final SrtpExtensionMessage msg;
    private final int protectionProfileLength = 2;

    public SrtpExtensionPreparator(TlsContext context, SrtpExtensionMessage message) {
        super(context, message);
        this.msg = message;
    }

    @Override
    public void prepareExtensionContent() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        for (SrtpProtectionProfiles profile : context.getConfig()
                .getSecureRealTimeTransportProtocolProtectionProfiles()) {
            byteStream.write(profile.getMinor());
            byteStream.write(profile.getMajor());
        }
        msg.setSrtpProtectionProfiles(byteStream.toByteArray());
        LOGGER.debug("Prepared the SRTP extension with protection profiles "
                + ArrayConverter.bytesToHexString(msg.getSrtpProtectionProfiles()));
        msg.setSrtpProtectionProfilesLength(msg.getSrtpProtectionProfiles().getValue().length);
        LOGGER.debug("Prepared the SRTP extension with protection profiles length "
                + msg.getSrtpProtectionProfilesLength().getValue());

        if (context.getConfig().getSecureRealTimeTransportProtocolMasterKeyIdentifier().length != 0) {
            msg.setSrtpMki(context.getConfig().getSecureRealTimeTransportProtocolMasterKeyIdentifier());
            LOGGER.debug("Prepared the SRTP extension with MKI " + ArrayConverter.bytesToHexString(msg.getSrtpMki()));
            msg.setSrtpMkiLength(msg.getSrtpMki().getValue().length);
            LOGGER.debug("Prepared the SRTP extension with mki length " + msg.getSrtpMkiLength().getValue());
        } else {
            msg.setSrtpMki(context.getConfig().getSecureRealTimeTransportProtocolMasterKeyIdentifier());
            msg.setSrtpMkiLength(0);
            LOGGER.debug("Prepared the SRTP extension with no MKI, hence the length is 0");
        }
    }

}
