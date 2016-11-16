/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2016 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0 http://www.apache.org/licenses/LICENSE-2.0
 */
package tlsattacker.fuzzer.workflow;

import de.rub.nds.tlsattacker.tls.constants.ConnectionEnd;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * An abstraction layer of a Handshake which represents a single message object
 * class and a direction that message was sent
 * 
 * @author Robert Merget - robert.merget@rub.de
 */
public class MessageFlow extends org.jgrapht.graph.DefaultEdge {

    /**
     *
     */
    private final Class<? extends Object> message;

    /**
     *
     */
    private final ConnectionEnd issuer;

    /**
     *
     */
    private int uniquer = 0;

    /**
     * 
     * @param message
     * @param issuer
     */
    public MessageFlow(Class<? extends Object> message, ConnectionEnd issuer) {
        this.message = message;
        this.issuer = issuer;
    }

    /**
     * 
     * @return
     */
    public int getUniquer() {
        return uniquer;
    }

    /**
     * 
     * @param uniquer
     */
    public void setUniquer(int uniquer) {
        this.uniquer = uniquer;
    }

    /**
     * 
     * @return
     */
    public Class<?> getMessage() {
        return message;
    }

    /**
     * 
     * @return
     */
    public ConnectionEnd getIssuer() {
        return issuer;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.message);
        hash = 47 * hash + Objects.hashCode(this.issuer);
        hash *= (1 + uniquer);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MessageFlow other = (MessageFlow) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (this.issuer != other.issuer) {
            return false;
        }
        return uniquer == other.getUniquer();
    }

    @Override
    public String toString() {
        return "" + message.getSimpleName() + ":" + issuer;
    }

    private static final Logger LOG = Logger.getLogger(MessageFlow.class.getName());

    @Override
    public Object clone() {
        return super.clone(); // To change body of generated methods, choose
        // Tools | Templates.
    }

}