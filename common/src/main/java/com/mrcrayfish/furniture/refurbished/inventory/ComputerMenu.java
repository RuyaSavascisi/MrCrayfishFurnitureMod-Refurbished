package com.mrcrayfish.furniture.refurbished.inventory;

import com.mrcrayfish.furniture.refurbished.blockentity.ComputerBlockEntity;
import com.mrcrayfish.furniture.refurbished.blockentity.IComputer;
import com.mrcrayfish.furniture.refurbished.client.ClientComputer;
import com.mrcrayfish.furniture.refurbished.core.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class ComputerMenu extends SimpleContainerMenu implements IElectricityMenu
{
    private final ContainerData data;
    private final IComputer computer;
    private ContainerListener changeListener;

    public ComputerMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data)
    {
        this(windowId, playerInventory, new SimpleContainerData(4), new ClientComputer(data.readBlockPos(), playerInventory.player));
    }

    public ComputerMenu(int windowId, Inventory playerInventory, ContainerData data, IComputer computer)
    {
        super(ModMenuTypes.COMPUTER.get(), windowId, new SimpleContainer(0));
        checkContainerDataCount(data, 4);
        this.data = data;
        this.computer = computer;
        this.addDataSlots(data);
        computer.setUser(playerInventory.player);
    }

    public IComputer getComputer()
    {
        return this.computer;
    }

    public void setChangeListener(ContainerListener changeListener)
    {
        this.changeListener = changeListener;
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        this.computer.setUser(null);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return this.computer.isValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex)
    {
        // Unused
        return ItemStack.EMPTY;
    }

    @Override
    public void setData(int dataIndex, int dataValue)
    {
        boolean changed = this.data.get(dataIndex) != dataValue;
        super.setData(dataIndex, dataValue);
        if(changed && this.changeListener != null)
        {
            this.changeListener.dataChanged(this, dataIndex, dataValue);
        }
    }

    public boolean getPowerState()
    {
        return (this.data.get(ComputerBlockEntity.DATA_SYSTEM) & 0xF) > 0;
    }

    public void setProgramData(long data)
    {
        this.data.set(ComputerBlockEntity.DATA_PROGRAM_1, (int) (data >> 32));
        this.data.set(ComputerBlockEntity.DATA_PROGRAM_2, (int) data);
    }

    public long getProgramData()
    {
        int programData1 = this.data.get(ComputerBlockEntity.DATA_PROGRAM_1);
        int programData2 = this.data.get(ComputerBlockEntity.DATA_PROGRAM_2);
        return (((long) programData1) << 32) | (programData2 & 0xFFFFFFFFL);
    }

    @Override
    public boolean isPowered()
    {
        return this.data.get(ComputerBlockEntity.DATA_POWERED) != 0;
    }
}
