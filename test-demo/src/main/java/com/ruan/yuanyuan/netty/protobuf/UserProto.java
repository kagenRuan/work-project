// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: UserProto.proto

package com.ruan.yuanyuan.netty.protobuf;

public final class UserProto {
  private UserProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface DemoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Demo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.Demo.OsType os_type = 10;</code>
     */
    int getOsTypeValue();
    /**
     * <code>.Demo.OsType os_type = 10;</code>
     */
    Demo.OsType getOsType();

    /**
     * <code>string id = 2;</code>
     */
    String getId();
    /**
     * <code>string id = 2;</code>
     */
    com.google.protobuf.ByteString
        getIdBytes();

    /**
     * <code>string did_md5 = 1;</code>
     */
    String getDidMd5();
    /**
     * <code>string did_md5 = 1;</code>
     */
    com.google.protobuf.ByteString
        getDidMd5Bytes();
  }
  /**
   * Protobuf type {@code Demo}
   */
  public  static final class Demo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Demo)
      DemoOrBuilder {
    // Use Demo.newBuilder() to construct.
    private Demo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Demo() {
      osType_ = 0;
      id_ = "";
      didMd5_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Demo(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              String s = input.readStringRequireUtf8();

              didMd5_ = s;
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              id_ = s;
              break;
            }
            case 80: {
              int rawValue = input.readEnum();

              osType_ = rawValue;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return UserProto.internal_static_Demo_descriptor;
    }

    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return UserProto.internal_static_Demo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Demo.class, Builder.class);
    }

    /**
     * Protobuf enum {@code Demo.OsType}
     */
    public enum OsType
        implements com.google.protobuf.ProtocolMessageEnum {
      /**
       * <code>UNKNOWN = 0;</code>
       */
      UNKNOWN(0),
      /**
       * <code>ANDROID = 1;</code>
       */
      ANDROID(1),
      /**
       * <code>IOS = 2;</code>
       */
      IOS(2),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>UNKNOWN = 0;</code>
       */
      public static final int UNKNOWN_VALUE = 0;
      /**
       * <code>ANDROID = 1;</code>
       */
      public static final int ANDROID_VALUE = 1;
      /**
       * <code>IOS = 2;</code>
       */
      public static final int IOS_VALUE = 2;


      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @Deprecated
      public static OsType valueOf(int value) {
        return forNumber(value);
      }

      public static OsType forNumber(int value) {
        switch (value) {
          case 0: return UNKNOWN;
          case 1: return ANDROID;
          case 2: return IOS;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<OsType>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          OsType> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<OsType>() {
              public OsType findValueByNumber(int number) {
                return OsType.forNumber(number);
              }
            };

      public final com.google.protobuf.Descriptors.EnumValueDescriptor
          getValueDescriptor() {
        return getDescriptor().getValues().get(ordinal());
      }
      public final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptorForType() {
        return getDescriptor();
      }
      public static final com.google.protobuf.Descriptors.EnumDescriptor
          getDescriptor() {
        return Demo.getDescriptor().getEnumTypes().get(0);
      }

      private static final OsType[] VALUES = values();

      public static OsType valueOf(
          com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
        if (desc.getType() != getDescriptor()) {
          throw new IllegalArgumentException(
            "EnumValueDescriptor is not for this type.");
        }
        if (desc.getIndex() == -1) {
          return UNRECOGNIZED;
        }
        return VALUES[desc.getIndex()];
      }

      private final int value;

      private OsType(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:Demo.OsType)
    }

    public static final int OS_TYPE_FIELD_NUMBER = 10;
    private int osType_;
    /**
     * <code>.Demo.OsType os_type = 10;</code>
     */
    public int getOsTypeValue() {
      return osType_;
    }
    /**
     * <code>.Demo.OsType os_type = 10;</code>
     */
    public OsType getOsType() {
      OsType result = OsType.valueOf(osType_);
      return result == null ? OsType.UNRECOGNIZED : result;
    }

    public static final int ID_FIELD_NUMBER = 2;
    private volatile Object id_;
    /**
     * <code>string id = 2;</code>
     */
    public String getId() {
      Object ref = id_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        id_ = s;
        return s;
      }
    }
    /**
     * <code>string id = 2;</code>
     */
    public com.google.protobuf.ByteString
        getIdBytes() {
      Object ref = id_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        id_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DID_MD5_FIELD_NUMBER = 1;
    private volatile Object didMd5_;
    /**
     * <code>string did_md5 = 1;</code>
     */
    public String getDidMd5() {
      Object ref = didMd5_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        didMd5_ = s;
        return s;
      }
    }
    /**
     * <code>string did_md5 = 1;</code>
     */
    public com.google.protobuf.ByteString
        getDidMd5Bytes() {
      Object ref = didMd5_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        didMd5_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getDidMd5Bytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, didMd5_);
      }
      if (!getIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, id_);
      }
      if (osType_ != OsType.UNKNOWN.getNumber()) {
        output.writeEnum(10, osType_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getDidMd5Bytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, didMd5_);
      }
      if (!getIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, id_);
      }
      if (osType_ != OsType.UNKNOWN.getNumber()) {
        size += com.google.protobuf.CodedOutputStream
          .computeEnumSize(10, osType_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Demo)) {
        return super.equals(obj);
      }
      Demo other = (Demo) obj;

      boolean result = true;
      result = result && osType_ == other.osType_;
      result = result && getId()
          .equals(other.getId());
      result = result && getDidMd5()
          .equals(other.getDidMd5());
      return result;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + OS_TYPE_FIELD_NUMBER;
      hash = (53 * hash) + osType_;
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId().hashCode();
      hash = (37 * hash) + DID_MD5_FIELD_NUMBER;
      hash = (53 * hash) + getDidMd5().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Demo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Demo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Demo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Demo parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Demo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Demo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Demo parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Demo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Demo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Demo parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Demo prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Demo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Demo)
        DemoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return UserProto.internal_static_Demo_descriptor;
      }

      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return UserProto.internal_static_Demo_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Demo.class, Builder.class);
      }

      // Construct using com.ruan.yuanyuan.netty.protobuf.UserProto.Demo.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        osType_ = 0;

        id_ = "";

        didMd5_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return UserProto.internal_static_Demo_descriptor;
      }

      public Demo getDefaultInstanceForType() {
        return Demo.getDefaultInstance();
      }

      public Demo build() {
        Demo result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public Demo buildPartial() {
        Demo result = new Demo(this);
        result.osType_ = osType_;
        result.id_ = id_;
        result.didMd5_ = didMd5_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Demo) {
          return mergeFrom((Demo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Demo other) {
        if (other == Demo.getDefaultInstance()) return this;
        if (other.osType_ != 0) {
          setOsTypeValue(other.getOsTypeValue());
        }
        if (!other.getId().isEmpty()) {
          id_ = other.id_;
          onChanged();
        }
        if (!other.getDidMd5().isEmpty()) {
          didMd5_ = other.didMd5_;
          onChanged();
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Demo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Demo) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int osType_ = 0;
      /**
       * <code>.Demo.OsType os_type = 10;</code>
       */
      public int getOsTypeValue() {
        return osType_;
      }
      /**
       * <code>.Demo.OsType os_type = 10;</code>
       */
      public Builder setOsTypeValue(int value) {
        osType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>.Demo.OsType os_type = 10;</code>
       */
      public OsType getOsType() {
        OsType result = OsType.valueOf(osType_);
        return result == null ? OsType.UNRECOGNIZED : result;
      }
      /**
       * <code>.Demo.OsType os_type = 10;</code>
       */
      public Builder setOsType(OsType value) {
        if (value == null) {
          throw new NullPointerException();
        }

        osType_ = value.getNumber();
        onChanged();
        return this;
      }
      /**
       * <code>.Demo.OsType os_type = 10;</code>
       */
      public Builder clearOsType() {

        osType_ = 0;
        onChanged();
        return this;
      }

      private Object id_ = "";
      /**
       * <code>string id = 2;</code>
       */
      public String getId() {
        Object ref = id_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          id_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string id = 2;</code>
       */
      public com.google.protobuf.ByteString
          getIdBytes() {
        Object ref = id_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          id_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string id = 2;</code>
       */
      public Builder setId(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }

        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string id = 2;</code>
       */
      public Builder clearId() {

        id_ = getDefaultInstance().getId();
        onChanged();
        return this;
      }
      /**
       * <code>string id = 2;</code>
       */
      public Builder setIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

        id_ = value;
        onChanged();
        return this;
      }

      private Object didMd5_ = "";
      /**
       * <code>string did_md5 = 1;</code>
       */
      public String getDidMd5() {
        Object ref = didMd5_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          didMd5_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string did_md5 = 1;</code>
       */
      public com.google.protobuf.ByteString
          getDidMd5Bytes() {
        Object ref = didMd5_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          didMd5_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string did_md5 = 1;</code>
       */
      public Builder setDidMd5(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }

        didMd5_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string did_md5 = 1;</code>
       */
      public Builder clearDidMd5() {

        didMd5_ = getDefaultInstance().getDidMd5();
        onChanged();
        return this;
      }
      /**
       * <code>string did_md5 = 1;</code>
       */
      public Builder setDidMd5Bytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

        didMd5_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:Demo)
    }

    // @@protoc_insertion_point(class_scope:Demo)
    private static final Demo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Demo();
    }

    public static Demo getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Demo>
        PARSER = new com.google.protobuf.AbstractParser<Demo>() {
      public Demo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Demo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Demo> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Demo> getParserForType() {
      return PARSER;
    }

    public Demo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Demo_descriptor;
  private static final
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Demo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\017UserProto.proto\"o\n\004Demo\022\035\n\007os_type\030\n \001" +
      "(\0162\014.Demo.OsType\022\n\n\002id\030\002 \001(\t\022\017\n\007did_md5\030" +
      "\001 \001(\t\"+\n\006OsType\022\013\n\007UNKNOWN\020\000\022\013\n\007ANDROID\020" +
      "\001\022\007\n\003IOS\020\002B-\n com.ruan.yuanyuan.netty.pr" +
      "otobufB\tUserProtob\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Demo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Demo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Demo_descriptor,
        new String[] { "OsType", "Id", "DidMd5", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}