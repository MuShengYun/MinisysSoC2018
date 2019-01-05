.data 0x0dfc

.text 0x0000
start:  addi    $sp,$zero,4000      #初始化$sp寄存器
        addi    $t0,$zero,0
        addi    $t1,$zero,0x0014
        lui     $t2,0x55aa
        xori    $t2,$t2,0x55aa
        lui     $t3,0x0aa55
        xori    $t3,$t3,0x0aa55
        addi    $s0,$zero,0x0e1     #RAM错误码
chkram: lw      $t8,0($t0)
        sw      $t2,0($t0)
        lw      $t4,0($t0)
        bne     $t4,$t2,0x0014      #跳转到error
        sw      $t3,0($t0)
        lw      $t4,0($t0)
        bne     $t4,$t3,0x0011      #跳转到error
        sw      $t8,0($t0)          #恢复原数值
        beq     $t0,$t1,0x0001      #检查完毕
        addi    $t0,$t0,0x1         #到下一个地址
        j       chkram
chkkey: lw      $t5,0xfffffc10($0)      #读键盘状态
        andi    $t0,$t5,0x1         
        bne     $t0,$zero,0x0a      #没有按键时读取按键信息，跳转到error
chkled: andi    $t0,$zero,0
        xori    $t1,$zero,0x02b66
dips:   addi    $t2,$zero,0x0005
        sw      $t0,0xfffffc60($zero)
lop:    addi    $t2,$t2,-1
        bne     $t2,$zero,-3
        addi    $t0,$t0,0x0457
        bne     $t0,$t1,-7          #跳转到dips标签
        addi    $zero,$zero,0x0     #nop
        #j       0x0022              #启动用户程序
        addi    $zero,$zero,0x0     #nop
error:  sw      $s0,0xfffffc00($zero)   #在数码管上显示错误码
        addi    $t0,$zero,0x1
        sw      $t0,0xfffffc00($zero)
