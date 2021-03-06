package twg2.jbcm.classFormat.constantPool;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import twg2.jbcm.classFormat.ClassFile;
import twg2.jbcm.classFormat.CpIndex;
import twg2.jbcm.classFormat.Settings;
import twg2.jbcm.classFormat.attributes.BootstrapMethod;
import twg2.jbcm.classFormat.attributes.BootstrapMethods;
import twg2.jbcm.modify.IndexUtility;

/** Java class file format constant pool <code>InvokeDynamic reference</code> info type
 * @author TeamworkGuy2
 * @since 2013-10-6
 */
public class CONSTANT_InvokeDynamic implements CONSTANT_CP_Info {
	public static final int CONSTANT_InvokeDynamic_info = 18;
	ClassFile resolver;

	byte tag = CONSTANT_InvokeDynamic_info;
	/* The value of the bootstrap_method_attr_index item must be a valid index into the bootstrap_methods
	 * array of the bootstrap method table (§4.7.21) of this class file. 
	 */
	short bootstrap_method_attr_index;
	/* The value of the name_and_type_index item must be a valid index into the constant_pool table.
	 * The constant_pool entry at that index must be a CONSTANT_NameAndType_info (§4.4.6) structure
	 * representing a method name and method descriptor (§4.3.3). 
	 */
	CpIndex<CONSTANT_NameAndType> name_and_type_index;


	public CONSTANT_InvokeDynamic(ClassFile resolver) {
		this.resolver = resolver;
	}


	@Override
	public int getTag() {
		return tag;
	}


	@Override
	public void changeCpIndex(short oldIndex, short newIndex) {
		IndexUtility.indexChange(name_and_type_index, oldIndex, newIndex);
	}


	public BootstrapMethod getBootstrapMethod() {
		return resolver.getBootstrapMethods().getBootstrapMethod(bootstrap_method_attr_index);
	}


	public CONSTANT_NameAndType getNameAndType() {
		return name_and_type_index.getCpObject();
	}


	public void setBootstrapMethodAttrIndex(int index) {
		resolver.getBootstrapMethods().getBootstrapMethod(index);
		this.bootstrap_method_attr_index = (short)index;
	}

	public void setNameAndTypeIndex(CpIndex<CONSTANT_NameAndType> index) {
		this.name_and_type_index = index;
	}


	@Override
	public void writeData(DataOutput out) throws IOException {
		out.write(CONSTANT_InvokeDynamic_info);
		out.writeShort(bootstrap_method_attr_index);
		name_and_type_index.writeData(out);
	}


	@Override
	public void readData(DataInput in) throws IOException {
		if(!Settings.cpTagRead) {
			int tagV = in.readByte();
			if(tagV != CONSTANT_InvokeDynamic_info) { throw new IllegalStateException("Illegal CONSTANT_Fieldref tag: " + tagV); }
		}
		bootstrap_method_attr_index = in.readShort();
		name_and_type_index = resolver.getCheckCpIndex(in.readShort(), CONSTANT_NameAndType.class);
	}


	@Override
	public String toString() {
		//return "CONSTANT_Fieldref(9, class=" + resolver.getConstantPool(class_index) + ", name_and_type=" + resolver.getConstantPool(name_and_type_index) + ")";
		BootstrapMethods bootstrapMethod = ((BootstrapMethods)resolver.getBootstrapMethods());
		CONSTANT_NameAndType method = name_and_type_index.getCpObject();
		return "InvokeDynamic(18, invoke_dynamic=" + bootstrapMethod.getBootstrapMethod(bootstrap_method_attr_index) +
				", name=" + method.getName() + ", type=" + method.getDescriptor() + ")";
	}

}
