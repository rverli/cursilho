package com.sicpa.thymeleaf.poc.aqualis.messages;

import java.io.Serializable;
import java.util.Arrays;

/**
 * A message to be displayed in web context. Depending on the type, different
 * style will be applied.
 */
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of the flash attribute.
	 */
	public static final String MESSAGE_ATTRIBUTE = "message";

	/**
	 * The type of the message to be displayed. The type is used to show message
	 * in a different style.
	 */
	public enum Type {
		DANGER("danger"), WARNING("warning"), INFO("info"), SUCCESS("success");
		
		private String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}

	private final String message;
	private final Type type;
	private final transient Object[] args;

	public Message(String message, Type type) {
		this.message = message;
		this.type = type;
		this.args = null;
	}

	public Message(String message, Type type, Object... args) {
		this.message = message;
		this.type = type;
		this.args = args;
	}

	public String getMessage() {
		return message;
	}

	public Type getType() {
		return type;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(args);
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (!Arrays.equals(args, other.args))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
}